# Learning Blog System

## How to clone repo

1. Navigate to your working directory
2. Open your command line and run: 

```
git clone https://github.com/toanthienla/learning-blog.git
```

## How to update back to repo

After making change to the project, run this code: 

```
git add .
git commit -m "YOUR_MESSAGE"
git push
``` 

## How to set up .env file

Create an .env file in  `YOUR_PROJECT/src/main/resources` 
An .env file should have be structured like this: 
```
DATABASE_NAME=YOUR_DATABASE_NAME
DATABASE_USERNAME=YOUR_DATABASE_USERNAME
DATABASE_PASSWORD=YOUR_DATABASE_PASSWORD
EMAIL=YOUR_EMAIL
APP_PASSWORD=YOUR_APP_PASSWORD
```
Replace the placeholder data with the actual data