param(
    [string]$OutputPath,
    [string]$PreviewPath
)

$ErrorActionPreference = 'Stop'

if ($PSVersionTable.PSEdition -eq 'Core') {
    $windowsPowerShell = Join-Path $env:WINDIR 'System32\WindowsPowerShell\v1.0\powershell.exe'
    $relaunchArguments = @(
        '-NoProfile',
        '-ExecutionPolicy', 'Bypass',
        '-File', $PSCommandPath
    )
    if (-not [string]::IsNullOrWhiteSpace($OutputPath)) {
        $relaunchArguments += @('-OutputPath', $OutputPath)
    }
    if (-not [string]::IsNullOrWhiteSpace($PreviewPath)) {
        $relaunchArguments += @('-PreviewPath', $PreviewPath)
    }
    & $windowsPowerShell @relaunchArguments
    exit $LASTEXITCODE
}

$projectRoot = Split-Path -Parent $PSScriptRoot
if ([string]::IsNullOrWhiteSpace($OutputPath)) {
    $OutputPath = Join-Path $projectRoot 'resource-pack\startup\highres-startupscreen.png'
}
if ([string]::IsNullOrWhiteSpace($PreviewPath)) {
    $PreviewPath = Join-Path $projectRoot 'assets\highres-startup-preview.png'
}

Add-Type -AssemblyName System.Drawing

if (-not ('HighResStartup.CheckerboardExtractor' -as [type])) {
    $compilerReferences = @(
        [System.Drawing.Bitmap].Assembly.Location,
        [System.Collections.Generic.Queue[int]].Assembly.Location
    )
    Add-Type -ReferencedAssemblies $compilerReferences -TypeDefinition @'
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;

namespace HighResStartup {
    public static class CheckerboardExtractor {
        public static Bitmap Extract(string path) {
            using (Bitmap input = new Bitmap(path)) {
                Bitmap image = new Bitmap(input.Width, input.Height,
                    PixelFormat.Format32bppArgb);
                using (Graphics graphics = Graphics.FromImage(image)) {
                    graphics.DrawImageUnscaled(input, 0, 0);
                }

                int width = image.Width;
                int height = image.Height;
                bool[] background = new bool[width * height];
                Queue<int> queue = new Queue<int>();

                for (int x = 0; x < width; x++) {
                    EnqueueIfBackground(image, x, 0, width, background, queue);
                    EnqueueIfBackground(image, x, height - 1, width, background, queue);
                }
                for (int y = 1; y < height - 1; y++) {
                    EnqueueIfBackground(image, 0, y, width, background, queue);
                    EnqueueIfBackground(image, width - 1, y, width, background, queue);
                }

                while (queue.Count > 0) {
                    int index = queue.Dequeue();
                    int x = index % width;
                    int y = index / width;
                    if (x > 0) EnqueueIfBackground(image, x - 1, y, width, background, queue);
                    if (x + 1 < width) EnqueueIfBackground(image, x + 1, y, width, background, queue);
                    if (y > 0) EnqueueIfBackground(image, x, y - 1, width, background, queue);
                    if (y + 1 < height) EnqueueIfBackground(image, x, y + 1, width, background, queue);
                }

                int left = width;
                int top = height;
                int right = -1;
                int bottom = -1;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int index = y * width + x;
                        if (background[index]) {
                            image.SetPixel(x, y, Color.Transparent);
                            continue;
                        }

                        Color pixel = image.GetPixel(x, y);
                        if (y >= 420 && !InsideSwordCore(x, y)) {
                            int maximum = Math.Max(pixel.R, Math.Max(pixel.G, pixel.B));
                            int minimum = Math.Min(pixel.R, Math.Min(pixel.G, pixel.B));
                            int chroma = maximum - minimum;
                            int brightness = (pixel.R + pixel.G + pixel.B) / 3;

                            // ImageGen returned a light checkerboard beneath the
                            // lower orange glow. Remove its neutral cells, then
                            // rebuild pale orange pixels as translucent glow so
                            // no rectangular raster pattern survives compositing.
                            if (chroma <= 12 && brightness > 145) {
                                image.SetPixel(x, y, Color.Transparent);
                                continue;
                            }
                            if (brightness > 170 && pixel.R > pixel.B + 8) {
                                int alpha = Math.Min(255,
                                    chroma * 4 + (255 - brightness) * 2);
                                if (alpha < 245) {
                                    image.SetPixel(x, y,
                                        Color.FromArgb(alpha, 255, 92, 0));
                                }
                            }
                        }

                        if (image.GetPixel(x, y).A > 0) {
                            left = Math.Min(left, x);
                            top = Math.Min(top, y);
                            right = Math.Max(right, x);
                            bottom = Math.Max(bottom, y);
                        }
                    }
                }

                if (right < left || bottom < top) return image;
                Rectangle bounds = Rectangle.FromLTRB(
                    Math.Max(0, left - 8), Math.Max(0, top - 8),
                    Math.Min(width, right + 9), Math.Min(height, bottom + 9));
                Bitmap cropped = image.Clone(bounds, PixelFormat.Format32bppArgb);
                image.Dispose();
                return cropped;
            }
        }

        private static bool InsideSwordCore(int x, int y) {
            if (y < 385 || y > 840) return false;
            if (y <= 515) return x >= 1185 && x <= 1300;
            return x >= 1220 && x <= 1275;
        }

        private static void EnqueueIfBackground(Bitmap image, int x, int y,
                int width, bool[] background, Queue<int> queue) {
            int index = y * width + x;
            if (background[index]) return;
            Color color = image.GetPixel(x, y);
            int maximum = Math.Max(color.R, Math.Max(color.G, color.B));
            int minimum = Math.Min(color.R, Math.Min(color.G, color.B));
            if (maximum - minimum > 7) return;
            background[index] = true;
            queue.Enqueue(index);
        }
    }
}
'@
}

function New-HighQualityGraphics([System.Drawing.Image]$image) {
    $graphics = [System.Drawing.Graphics]::FromImage($image)
    $graphics.CompositingMode = [System.Drawing.Drawing2D.CompositingMode]::SourceOver
    $graphics.CompositingQuality = [System.Drawing.Drawing2D.CompositingQuality]::HighQuality
    $graphics.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
    $graphics.PixelOffsetMode = [System.Drawing.Drawing2D.PixelOffsetMode]::HighQuality
    $graphics.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::HighQuality
    $graphics.TextRenderingHint = [System.Drawing.Text.TextRenderingHint]::AntiAliasGridFit
    return $graphics
}

function Draw-CenteredLayer(
    [System.Drawing.Graphics]$Graphics,
    [System.Drawing.Image]$Layer,
    [int]$TargetWidth,
    [int]$Top
) {
    $targetHeight = [int][Math]::Round($TargetWidth * $Layer.Height / $Layer.Width)
    $left = [int][Math]::Round((3840 - $TargetWidth) / 2)
    $Graphics.DrawImage($Layer, $left, $Top, $TargetWidth, $targetHeight)
}

function Draw-WurmServerMark(
    [System.Drawing.Graphics]$Graphics,
    [System.Drawing.Image]$WurmLogo
) {
    $logoWidth = 620
    $logoHeight = [int][Math]::Round($logoWidth * $WurmLogo.Height / $WurmLogo.Width)
    $Graphics.DrawImage($WurmLogo, 215, 102, $logoWidth, $logoHeight)

    $font = [System.Drawing.Font]::new(
        'Georgia', 60, [System.Drawing.FontStyle]::Bold,
        [System.Drawing.GraphicsUnit]::Pixel)
    $format = [System.Drawing.StringFormat]::new()
    $format.Alignment = [System.Drawing.StringAlignment]::Center
    $format.LineAlignment = [System.Drawing.StringAlignment]::Center
    $shadow = [System.Drawing.SolidBrush]::new(
        [System.Drawing.Color]::FromArgb(225, 0, 0, 0))
    $lettering = [System.Drawing.SolidBrush]::new(
        [System.Drawing.Color]::FromArgb(255, 242, 210, 141))
    try {
        $shadowRect = [System.Drawing.RectangleF]::new(205, 414, 654, 105)
        $textRect = [System.Drawing.RectangleF]::new(198, 405, 654, 105)
        $Graphics.DrawString('SERVER', $font, $shadow, $shadowRect, $format)
        $Graphics.DrawString('SERVER', $font, $lettering, $textRect, $format)
    } finally {
        $lettering.Dispose()
        $shadow.Dispose()
        $format.Dispose()
        $font.Dispose()
    }
}

$backgroundPath = Join-Path $projectRoot 'assets\sources\startup-background-imagegen-v6-god-avatars.png'
$wurmLogoPath = Join-Path $projectRoot 'assets\sources\wurm-unlimited-logo-imagegen.png'
$sklotopolisPath = Join-Path $projectRoot 'assets\sources\sklotopolis-wordmark-imagegen.png'

$background = [System.Drawing.Bitmap]::FromFile($backgroundPath)
$wurmLogo = [HighResStartup.CheckerboardExtractor]::Extract($wurmLogoPath)
$sklotopolis = [System.Drawing.Bitmap]::FromFile($sklotopolisPath)
$canvas = [System.Drawing.Bitmap]::new(3840, 2160,
    [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)

try {
    $graphics = New-HighQualityGraphics $canvas
    try {
        $graphics.DrawImage($background, 0, 0, 3840, 2160)
        Draw-WurmServerMark $graphics $wurmLogo
        Draw-CenteredLayer $graphics $sklotopolis 1900 20
    } finally {
        $graphics.Dispose()
    }

    $outputDirectory = Split-Path -Parent $OutputPath
    $previewDirectory = Split-Path -Parent $PreviewPath
    [System.IO.Directory]::CreateDirectory($outputDirectory) | Out-Null
    [System.IO.Directory]::CreateDirectory($previewDirectory) | Out-Null
    $canvas.Save($OutputPath, [System.Drawing.Imaging.ImageFormat]::Png)

    $preview = [System.Drawing.Bitmap]::new(1920, 1080,
        [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
    try {
        $previewGraphics = New-HighQualityGraphics $preview
        try {
            $previewGraphics.DrawImage($canvas, 0, 0, 1920, 1080)
        } finally {
            $previewGraphics.Dispose()
        }
        $preview.Save($PreviewPath, [System.Drawing.Imaging.ImageFormat]::Png)
    } finally {
        $preview.Dispose()
    }
} finally {
    $canvas.Dispose()
    $sklotopolis.Dispose()
    $wurmLogo.Dispose()
    $background.Dispose()
}

Write-Output "Created $OutputPath"
Write-Output "Created $PreviewPath"
