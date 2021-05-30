# Запуск приложения

Для создания jar файла в терминале напишите

```bash
mvn clean install
```

Для ознакомления с функционалом пишем

```bash
java -jar target/*.jar --help 
```
Для ознакомления с функционалом определенной команды пишем

```bash
java -jar target/*.jar crop --help 
```

Для обработки интересующего Вас изображения указываем:
- путь к изображению (--imageIn src/test/resources/testImage.jpg)
- высоту изображения, если хотим изменить ее (--tH 500)
- ширину изображения, если хотим изменить ее (--tW 500)
- размытие изображения, при необходимости (--bl 0)
- уровень сжатия отвечающий за качество изображения (--qu 100)
- если хотим вырезать прямоугольную область изображения задаем начальную точку отсечения с помощь x,y и ширину с высотой (--crop crop --x 5 --y 0 -cH 200 -cW 400)


p.s.: Отредактированые изображения попадают в теже папки что и оригинал


Вырезаем прямоугольную область изображения

```bash
java -jar target/*.jar crop --image src/test/resources/testImage.jpg --tW 800 --tH 700 --x 10 --y 100
```

Размываем изображения

```bash
java -jar target/*.jar blur --image src/test/resources/testImage.jpg --bl 30 --x 200 --y 400 --tH 200 --tW 200
```

Задаем уровень сжатия изображения, уменьшаем/увеличиваем картинку

```bash
java -jar target/*.jar quality --image src/test/resources/testImage.jpg --bl 0 --qu 70 --tH 200 --tW 200
```

Задаем необходимый размер для изображения

```bash
java -jar target/*.jar resizes --image src/test/resources/testImage.jpg --tW 600 --tH 600
```
