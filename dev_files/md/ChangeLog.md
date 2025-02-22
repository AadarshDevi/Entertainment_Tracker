# Jan 3, 2025

- Project Created

- Project Files created
- files created:

  - Java Files:
    - api:
      - API
      - Client
    - backend:
      - Backend
      - entertainment:
        - Entertainment
        - Movie
        - Anime
  - res:
    - fxml:
      - MainFrame
      - Module
      - ModuleTab
  - test.txt

- Basic Code written:

  - API
  - Client

- Backend.java:

  - data reading
  - 2 levels of parsing

- Entertainment classes completely changed and rewritten
- entertainment data format completely changed and rewritten

# Jan 10, 2025

- mainframe ui in client rerouted to MainFrame.fxml
- module controller added
- created movie parsing
- mainframe ui fixed
- SearchEngine.java file created
- ConsoleLog.java file created
- mainframe controller added
- FXMLS edited extensively
- (FIXME) the last module in the list is showing its information and the ones before it don't show it. only the last module is showing the information.

# Jan 12, 2025

- fixed movie format by adding animation companies
- commenting Backend.java for better understanding of Problem #1.
- Problem #1 fixed:
  > objects declared first. in the for each entertainment in entertainments, instantiated each declared object. this includes the borderpane (module), module controller, module fxml loader.
- finished commenting most of the code in Backend.java
- to put the same modules in search list and other list, the module had to be cloned. the modules in search list are cloned from the original data modules.
- entertwainment put into module after module is created by fxml
- released date was moved from movie, anime to entertainment

- mainframe.fxml fixe:
  > removed scrollpane because listview has scrolling

# Jan 13, 2025

- Built SearchEngine and corresponding ui changes.
- (FIXME) Results are showing 0 instead of 5 (5 Movies in list)
  > it works when the logging is present.
- Somehow fixed the problem.
- Code Files Changed:
  - SearchEngine
  - MainFrameController
- module information partially viewable. (most information)
- date is used to check if the entertainment is released or not.

- when the duration is set to 0, the viewer and module will display unknown.
- when the date is set to 3000-12-10, the viewer and module will display unknown.

---

# Jan 15, 2025

## Added

- ChangeLog.md
- TODO.md
- centerlabel and its controller created and populated
- tags and production companies are added to their respective tabs.

## Fixed

- improved the search results.

  > the modules that dont have the same id as the current id from the results array are removed. the search results will remove the module by the object and it will not go through every module to find the module with the same id.

  > if the length of the string is 0, the SearchEngine, API and MainFrameController will stop the search

- Search bar will automatically search when typing. (MainFrame.fxml)
- animeviewer buttons on the bottom will now be the right width. (AnimeViewer.fxml)
- copy button in viewers for entertainment are used for the copy entertainment name using same logixc used when showing it in the module.
- formatting from data file fixed in Movie, Anime viewer and Module.

- button layout for anime viewer fxml fixed.

---

# Jan 24, 25, 26, 2025

## Added

- UI for adding / editing entertainments
- controller
- date converstion re-written
- app code reorganized
- reading from file is much faster
- flow chart created for the startup of the app.
  > check the app_start.txt
- app startup reorganized
-
- code re-written

  - client
  - api
  - mainframe controller
  - backend
  - module controller

- SearchEngine and SearchHistory created.

  > the engine uses incremental search. search engine uses characters and a search history to do searches.

- search engine uses words to search through the data.

## Removed

## Fixed

## Problems

- search engine unable to be integrated because the modules in complted list are having their data removed. and in search list, all the modules are removed.

---

# Feb 22, 2025

## Added

- a simple search algorithm instead of the old incremental search

- movies can be viewed in the viewer. (but not animes/tv shows)

## Removed

- the ability to view animes
- the ability to view movies

- app will not read anything other than movies

## Fixed

- lists were refreshed fixing invisible modules
- the wrong module list was being put in search list.
  > the modules list (containing all modules in the other three lists other than search) was being used to add in search list. (search list modules were not used before fix)

## Problems

- Modules in completed, released upcoming and search lists were being invisible
- when searching modules from other lists were added in it.

---

# mmm dd, yyyy

---

# mmm dd, yyyy

---

# mmm dd, yyyy

---

# mmm dd, yyyy

---

# mmm dd, yyyy
