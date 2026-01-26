## Job4j Boost Mood Bot

A Telegram bot designed to track your daily mood, log entries, and send motivational rewards (audio, photos, text) when you hit streaks of good mood days. Its ultimate goal: be the friend in your pocket that brightens your day with humor, sympathy, and support.

### Features
- User Registration: New users register with /start.
- Daily Mood Logging: Choose your mood.
- Mood History: View your mood log for the last week (/week) or month (/month).
- Streak Rewards: Automatic awards for consecutive good mood days.
- Mood Boosters: On-demand humor, audio, and visual content to lift spirits.
  
### Installation Instructions

Follow these steps to run the project locally:

1) Clone the repository:
```
git clone <repository-url>
```
2) Get Bot Token:

Message @BotFather on Telegram

/newbot → follow prompts → copy token
3) Configure:
Edit src/main/resources/application.properties by adding your own Telegram bot token:
```
telegram.bot.token=.....
```
4) Launch the application:

You can start the application using one of the following methods:

- Using the Main class:

Navigate to src/main/java/ru/job4j/bmb and run the Main class.

- Via Maven:

Compile and run the project with:
```
mvn spring-boot:run
```
6) Access the application:

Open your bot on Telegram and press start!

### Technologies Used

Java 21

Spring Boot 3.3.3

H2 (embedded) + Spring Data JPA

telegrambots 6.9.7.1

JUnit 5, Mockito, AssertJ

Checkstyle 10.3.3

Lombok, AOP

### Contact
Katya Shcherbakova

You can reach out via Telegram: @Midori_Sun

![Screenshot_1]()

![Screenshot_9]()

![Screenshot_13]()

![Screenshot_14]()
