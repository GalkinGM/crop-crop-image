# Запуск приложения

Для создания jar файла в терминале напишите

```bash
mvn clean install
```

Для ознакомления с функционалом пишем

```bash
java -jar target/*.jar --help 
```
Для обработки интересующего Вас изображения указываем:
- путь к изображению (--imageIn src/test/resources/testImage.jpg)
- высоту изображения, если хотим изменить ее (--tH 500)
- ширину изображения, если хотим изменить ее (--tW 500)
- размытие изображения, при необходимости (--bl 0)
- уровень сжатия отвечающий за качество изображения (--qu 100)
- если хотим вырезать прямоугольную область изображения задаем начальную точку отсечения с помощь x,y и ширину с высотой (--crop crop --x 5 --y 0 -cH 200 -cW 400)
- имя отредактированного файла для сохранения(-s testerSave)

p.s.: Отредактированые изображения попадают в папку src/test/resources


Примеры

```bash
java -jar target/*.jar --imageIn src/test/resources/testImage.jpg --tW 500 --tH 500 --qu 100 --bl 0 -s testerSave

java -jar target/*.jar --imageIn src/test/resources/testImage.jpg --qu 100 --bl 10 --crop crop --x 5 --y 0 -cH 200 -cW 600 -s testerSave2
```

# Greetings, traveller

Второй этап отбора на направление «Бэкенд разработка» в Летнюю школу [МТС.Тета](http://teta.mts.ru/).

## Сrop-crop-resize-resize

В качестве задания предлагается выполнить проект «crop-crop». Эта утилита позволит ресайзить изображение для остальных сервисов МТС: изменение изображений рекламных баннеров, превью для альбомов и обложки фильмов в маленьком разрешении и другие.

## Используемые технологии

- Код приложения пишется на Java.
- Библиотеки, которые можно использовать:
    - Работа с консольными параметрами [picocli.info](https://picocli.info/).
    - Работа с изображением [thumbnailator](https://github.com/coobird/thumbnailator). Рекомендуем взять из неё функции обрезки  и изменения параметров картинки.
    - Библиотека для работы с изображениями и видео [marvin project](https://github.com/gabrielarchanjo/marvin-framework). Рекомендуем взять из неё  GaussianBlur и Crop.
    - Библиотека, необходимая для запуска тестов [junit5](https://github.com/junit-team/junit5).

## Интерфейс взаимодействия

```bash
Version: name version https://gitlab.com/link/
Available formats: jpeg png webp
Usage: convert input-file [options ...] output-file
Options Settings:
  --resize width height       resize the image
  --quality value             PEG/PNG compression level
  --crop width height x y     сut out one or more rectangular regions of the image
  --blur {radius}             reduce image noise and reduce detail levels 
  --format "outputFormat"     the image format type
```

## Описание параметров

**--resize width height** — уменьшает, увеличивает картинку или задает необходимый размер для изображения. [Пример в документации](https://imagemagick.org/script/command-line-options.php#resize).

**--quality value** — задает уровень сжатия файлов JPEG / PNG. Форматы изображений могут быть JPEG и PNG, качество от 1 (самое низкое качество изображения и самое высокое сжатие) до 100 (лучшее качество, но наименее эффективное сжатие). [Пример в документации](https://imagemagick.org/script/command-line-options.php#quality).

**--crop width height x y** —  Вырезает прямоугольную область изображения. Обработанное изображения должно иметь ширину(**width**) и высоту(**height**). Точка отсчета задаётся значениями **x** и **y.** [Пример из документации](https://imagemagick.org/script/command-line-options.php#crop).

**--blur radius** — добавляет размытие или увеличивает резкость. [Пример в документации](https://imagemagick.org/script/command-line-options.php#blur).

**--format "outputFormat"** — конвертирует изображение в "outputFormat". Параметр "outputFormat" может быть JPEG / PNG. [Пример в документации](https://imagemagick.org/script/command-line-options.php#format).

Валидация входных параметров должна быть реализована. 

## Требования к проекту

1. Код проекта должен быть аккуратным, без дублирования. Наличие больших повторяющихся фрагментов кода может быть причиной снижения баллов.
2. Проект не должен отдавать изображение более, чем за 350 миллисекунд.

## Полная версия технического задания
Ты сможешь её найти [по ссылке](https://www.notion.so/edtech17/public-4a6da22b5a36489c99b9b986a4c9d7cb).
