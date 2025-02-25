# Entertainment Tracker To-Do List

## List

### Anime Addons

- [ ] show anime/tv shows in viewer when module is clicked

  > all of the work for this is in mainframe

- [ ] create Episodes so i can add miraculous ladybug season 6 eps and release dates, duration and ep number
- [ ] reenable app to read anime and tv shows

### Entertainment Creator, Editor

- [x] entertainment editor's slider needs 3 new buttons:

  > [x] Slider UI Format >> to first (<<) | back (<) | field | search (search icon) | next (>) | last (>>)

- [x] add save button

### Data Writing and Formating

- [ ] toString will be int the data format (see data.txt)
- [ ] make the writeData method to write the data back to data.txt
- [ ] make app take data backups.
- [ ] create file to store settings information and read it after app is set up

  > for writing data to data.txt

### Other

- [ ] make code modular
  > rewrite the viewing sequence
- [ ] convert project into an .exe using Launch4J
<!-- > can be viewed like the tags -->

## Completed

### Feb 24, 2025

- [x] show entertainment in editor when edit button for the entertainment is clicked
- [x] editing entertainments should use the entertainment list for easy use when bulk editing
- [x] checkboxes selected/deselected and/or enabled/disabled based on primary and secondary statuses

- [x] use indexing (module ids) for editing instead of entertainments

  > it will use entertainment from the ogSearchModules (search tab, mainframe)

### Feb 22, 2025

- [x] write new search algorithm
- [x] integrate new search algorithm in app

- [x] when searching, make the search tab active.

  > if i am on completed, etc tab and search "text", it should go to search tab and show the results

- [x] show movies in viewer when module is clicked
  > all of the work for this is in mainframe

### Jan 15, 2025

- [x] write the toString for the entertainments

- [x] duplicate modules and put it in search tab
- [x] Make module send the tags and production companies so viewer can view them.
- [x] set viewers and editor's fxml and controllers
- [x] make the ui for editing a movie, anime

### Jan 03, 2025 - Jan 13, 2025

- [x] Entertainment Viewer (MovieViewer/AnimeViewer) view the data sent from module

  > the goal is to view the infomation in the module using the information viewer. the viewer will change based on the type of the entertainment. if the type is movie, the viewer will be MovieViewer.

- [x] changed scrollpane and gridpane to listview.
- [x] use fxml for module and put info to it.

  > Put the entertainment in the module after making it usign fxml and viewing it when using app.

- [x] use date to check if the entertainment is out or not released yet.
- [x] show modules from search.

  > when the search results have returned from search engine, use it to remove unwanted modules and keep the ones needed.

  - [x] add movie viewer to the app.
  - [x] add anime viewer to the app.
