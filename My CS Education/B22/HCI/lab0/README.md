# lab0: Getting started 


## Goals

This lab aims to make sure that you have the programming infrastructure to complete the upcoming assignments. The simple components included in the test code will ensure that you are set up to do web development and testing on a mobile device or emulator. 

If you run into any issues with your setup, please post to the discussion boards in Canvas, instead of emailing the teaching staff. The teaching staff will be monitoring the discussion board and will respond. However, some issues are very specific to a particular setup and so there may be another student who has run into a similar issue and can help. Similarly, please check the discussion board to see if you can help any of your fellow students with their setup.

## Getting started with Github and gh-pages
You will use [Github](https://github.com/) to host your lab directories and your web page. 
If you need a Git/GitHub refresher, see this GitHub Bootcamp, the GitHub Guides (especially the ones on Hello World, and Understanding the GitHub Flow), and [CodeSchool's Try Git Course](https://www.pluralsight.com/courses/how-git-works).

## Text Editor
You will require an editor to edit and create html files. You can use built in editors like Notepad or TextEditor or download a new one like Notepad++, [Sublime Text](https://www.sublimetext.com/download), or [VS Code](https://code.visualstudio.com/download) for this. 
See this [HTML Editors page](https://www.w3schools.com/html/html_editors.asp)

You can also follow the steps below to set up your first web page: 
### 1. Create a Github account

### 2. Fork this project to your account
The fork button is located on the top right of this page. [help](https://help.github.com/en/github/getting-started-with-github/fork-a-repo) 

### 3. Clone and edit your project

  a. Github is based on Git. You need to have [Git](https://git-scm.com/) installed to be able to clone your project locally on your computer

  b. Copy the link under *Code -> Clone or Download -> HTTPS*

  c. On your computer open a terminal in the directory where you would like to have your project folder and use the command `git clone link-to-YOUR-copy-of-the-repo` to clone your repository
  
  **Note:** If you are unable to successfuly clone your repository with your password and you are required to provide a [personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token). You may generate one using the following steps
  
      i. Click your profile picture at the top right of your profile and a dropdown box will appear
      
      ii. Click on Settings
      
      iii. On the left of your settings screen, select Developer settings
      
      iv. In your Developer settings, select Personal Access Tokens > Tokens (classic)
      
      v. Select Generate new token > Generate new token (classic)
      
      vii. Provide a reason for why you need the token, select the privileges you want the token to provide and finally at the bottom of your screen, Select Generate token
      
      viii. Use the generated token as your password when cloning your repository using Git

  d. Move into your *lab0* directory and create an *index.html* file with the same content as [the one at the bottom of this page](https://github.com/cs3041-b22/lab0/blob/main/README.md#indexhtml-code). You can use any editor mentioned [here](https://github.com/cs3041-b22/lab0/blob/main/README.md#Text-Editor). Save your edit.

  e. Whenever you want to submit your local changes to your remote repository, open a terminal inside your project and type the following commands: 
```
git add * 
git commit -m "your commit message here should be helpful"
git push 
```
Now if you refresh your Github repository, you shall see the newly uploaded html file

#### Alternative option for step 3e: Go to your Github repository, click the 'Add file' button and import your index.html file and/or other files you updated from your computer.

### 4. Create your gh-pages branch

#### Option1️⃣: from your local computer 
In your terminal again, type the following commands
```
git checkout -b gh-pages         // create the new branch gh-pages
git status 
git merge main
git push --set-upstream origin gh-pages   // to do only the first time you push to a new branch otherwise just `git push` will do
```
#### Option 2️⃣: online on Github
  a. Create a new branch called *gh-pages* in your repository and make sure its content is the same as the main branch. 
![](https://github.com/cs3041-b22/lab0/blob/main/img/image7.gif)

### 5. Access your web page

#### Option 1️⃣: locally 
While you are coding, you can always test your webpage locally by opening your index.html with your browser. 

#### Option 2️⃣: online
Testing your Github page online is only possible once you pushed your changes to the *gh-pages* branch. Type the following url in your browser:  `yourGithubUsername.github.io/lab0/index.html`
Replace yourGithubUsername with your actual Github username

**Note**: Sometimes it might take a while for the latest version to refresh and load. 
![](https://github.com/cs3041-b22/lab0/blob/main/img/image6.gif)

**Note:** If you are getting an error when trying to load your page
1. Navigate here: https://github.com/yourGithubUsername/lab0/settings/pages - Make sure to change yourGithubUsername to your GitHub username.

2. It will ask you which branch you are working in for your project  (e.g. gh-pages).

3. Click save.

## Set up testing environment
#### 1. Set up **testing environments** for iOS and Android devices

a. For this class, we will mostly use Chrome Developer tools, which allow you to easily test your UI to see how it will look on different devices. 
- Open any website in Chrome. You can use the test page that you just created (https://yourGithubUsername.github.io/lab0/index.html) - Make sure to replace yourGithubUsername with your own GitHub username.
- View -> Developer Tools -> Developer Tools (you can also do *ctrl+shift+i* or *Right-click on the page > inspect*
        -- (Note: If you don't see this: there may be "three dots" at the right corner of your screen, -> More tools -> Developer Tools)
- Use button to toggle device toolbar ![](https://github.com/cs3041-b22/lab0/blob/main/img/image3.png) . (you can also do *ctrl+shift+m*)
- You can test different types of devices. For example, to test iPhone, select iPhone SE from dropdown list: 
![](https://github.com/cs3041-b22/lab0/blob/main/img/image2.png)
- Click on the three dots menu for more options. (Note: there may be other “three dots” menus on your screen. Make sure that you select the one next to the buttons shown in the screenshot below.) Then click on “Show Device Frame” which shows you the hardware:
![](https://github.com/cs3041-b22/lab0/blob/main/img/image5.png)
  
b. Rotate the device by clicking on ![](https://github.com/cs3041-b22/lab0/blob/main/img/image4.png) button
  
c. **Note:** It is always best to test on an actual device as a simulator will never give you the full experience. So, if you have an iphone or iPad to test on, that is even better. 
  
d. Another option for Mac users: You can download Xcode and use the iOS simulator
- Instructions [here](https://developer.apple.com/library/archive/referencelibrary/GettingStarted/DevelopiOSAppsSwift/)
- Navigate to any website to test
- To use the hardware buttons (e.g. Home, Siri, etc.), use “Hardware” Menu

## Submit your work
On canvas, submit the following: 
- a link to your Github repository
- a link to your web page

#### 2. More resources on how to work locally with Git: 
- https://www.jquery-az.com/3-examples-of-git-create-new-branch/
- https://desktop.github.com/
- Some IDEs like Visual Studio Code already have integrated support for version control like Git. More on that [here](https://code.visualstudio.com/docs/editor/versioncontrol) 

##### index.html code
```
<!DOCTYPE html>
<html>
<head>
    <title> CS 3041 </title>
</head>
<body>
    <!-- HTML5 article tag for content -->
   <div id="content">
   
   <header>My CS3041 test page</header>

   <!-- H1 means 1st level heading -->
   <h1>Welcome to my CS3041 test page</h1>
    
   <!-- P stands for paragraph -->
   <p>This is just a test, and it looks like it is working!</p>
  
   <footer>CS3041: Human-Computer Interaction &copy; 2022</footer>
    </div>
</body>
</html>
```


