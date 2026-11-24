# UAS Spring AI

Proyek UAS berdasarkan buku "Spring AI in Action" (Craig Walls):
- Chapter 1: Getting started with Spring AI
- Chapter 2: Evaluating generated responses

## Prasyarat
- JDK 21, Git
- API key OpenAI

## Cara menjalankan
1. Atur API key (PowerShell):
   `$env:SPRING_AI_OPENAI_API_KEY="sk-nry-BazRptLf3Vz0BoJItsMovKojob0I53fZWR8S49Fb8HI"`
2. Jalankan test: `.\gradlew test`
3. Jalankan aplikasi: `.\gradlew bootRun`
4. Kirim pertanyaan:
   `Invoke-RestMethod -Uri http://localhost:8080/ask -Method Post -ContentType "application/json" -Body '{"question":"Why is the sky blue?"}'`

## Versi
- Spring Boot 3.5.5, Spring AI 1.0.1
- Tag `chapter-1` dan `chapter-2` menandai hasil tiap chapter

## Dokumentasi
- Laporan, screenshot, dan diagram UML ada di folder `docs/`

## Pembuat
Sandy Karunia Abadi, 12409011030076
