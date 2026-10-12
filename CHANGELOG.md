# Changelog

## 0.4.3 — 2026-10-13

- Rebuild with the complete canonical Chamomilo updater 1.5.0 and its shared Settings editor.
- Verify that the packaged mod registers both settings, saves explicit screen overrides and keeps the active image unchanged until restart.
- Include the startup-catalogue crash fix from 0.4.2.
- Replace the illustrated README and installation ZIP's plain-text guide; remove temporary diagnostics, build caches and obsolete distributions from the project folder.
- Keep the installable ZIP limited to the descriptor, two runtime JARs and README.txt.

## 0.4.2 — 2026-10-13

- Fix a client startup crash in 0.4.1: Wurm's shared Javassist loader could not find the screen catalogue through the standard resource API.
- Read the catalogue beside the library class in the active mod JAR when the standard resource lookup is unavailable.
- Verify the packaged mod's configuration, lifecycle, renderer hook, 4K image and reconnect caching through Wurm's real loader, with no mod resources on the parent classpath.

## 0.4.1 — 2026-10-13

- Rebuild and verify the complete canonical Chamomilo updater 1.5.0 in the mod JAR and installation ZIP.
- Rewrite the illustrated installation guide and the plain-text README included in the ZIP.
- Remove obsolete binaries, artwork working files and redundant build helpers from the project.
- Keep the installation ZIP limited to the descriptor, two runtime JARs and README.txt.

## 0.4.0 — 2026-10-13

- Add Wurm Reimagined, new artwork based on the standard Wurm Unlimited loading screen.
- Introduce a catalogue of two 3840 × 2160 screens, preserving the existing Sklotopolis artwork.
- Choose a screen randomly and equally on each client launch by default. Keep it stable on reconnect.
- Allow a fixed screen through the shared registry Settings editor; apply selection changes on restart.
- Try the remaining library images if one is missing, corrupt or has invalid dimensions, then fall back to the stock screen.
- Verify every library image, fixed and random selection, reconnect caching and archive packaging.

## 0.3.10 — 2026-10-13

- Add the shared registry Settings editor with every user-configurable property, hover help and immediate application where supported.
- Warn immediately after a saved startup-only change and offer the standard Wurm quit confirmation after settings are saved.

## 0.3.9 — 2026-10-12

- Embed the complete Chamomilo updater 1.4.1, including early election of the
  newest compatible updater across installed Chamomilo mods.
- Keep the default resource-pack filename aligned with the mod version.
- Replace the installation guide with an illustrated repository README and a
  plain-text README in the ZIP.
- Package only the descriptor, two runtime JARs and README.txt. Include the
  full GPL license inside the mod JAR.

## 0.3.8 — 2026-10-10

- Embed the complete canonical updater 1.2.6 with no Off choice. The updater
  is the only language selector for capable mods and applies a concrete shared
  language at startup and after every choice. Retain exact module/hash checks.



## 0.3.7

- Replace the previous updater with the current shared registry, native Mod updates menu and saved startup preference.
- Reuse the original button artwork with complete top/bottom borders and stable horizontal stretching.
- Verify native layout, input, HUD replacement and shared updater packaging before distribution.
