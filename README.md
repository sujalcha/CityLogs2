# CityLogs

The developed application “CityLogs”, is a mobile application that records the data of 5 different cities into the database i.e. Perth, Brisbane, Sydney, Melbourne, Adelaide. This application is simple and easy to use with various inbuilt features. The input data is taken from the user and stored in the local database and this stored data can be used for further purposes. The application also has the feature of registering user profile where user can register their username along with their unique password. However, according to the assignment criteria, this section does not incorporate with the log entry section. The application is for city dwellers where they can log into their account and record their starting city name, date and time, contact number, invoice number and their final destination. The user only has to press the time button, type their contact number and invoice details and choose the required destination value through spinner option. The individual pages of the application are discussed below.

Home page: Home page is the first fragment layout page that appears when application starts. Here, the users can choose the staring city. User can choose any city button and this leads to the respective individual city fragment layout.

Landing page: This fragment appears when the user clicks on any of the city buttons in the home fragment layout. Here user can record the date and time, their contact and invoice number and the final destination values. They can also save log, view log, change the City etc.   

Process of log Entry The user has to enter every value only then the log data could be saved. Otherwise the application will show a validation error message.

Saved Log: This is how the citylist layout is displayed when we click the show log button in the city fragment layout. The data that are saved with the save entries menu function in the database are extracted and showed in this city list fragment.
