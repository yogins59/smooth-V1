# Smooth60 V1 Prototype

Prototype Android untuk menguji fondasi aplikasi frame-smoothing.

## V1 saat ini
- Native Kotlin + Android Studio
- Foreground service
- Permission overlay
- Floating FPS/status overlay
- MediaProjection permission flow
- Struktur siap dikembangkan menjadi frame-processing pipeline

## Penting
V1 ini BELUM melakukan true 30->60 FPS interpolation.
MediaProjection session/frame pipeline sengaja menjadi tahap berikutnya setelah overlay + service terbukti stabil.

## Build
Buka folder ini di Android Studio, tunggu Gradle Sync, lalu Build > Make Project.
