import DBUtil.DButil;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class HW3 extends Application {
    // initialize global variable
    Set<String> selectedGenres = new HashSet<>();
    Set<String> selectedCountries = new HashSet<>();
    Set<String> selectedLocations = new HashSet<>();
    Set<String> selectedTags = new HashSet<>();

    // Rating
    ComboBox<String> ratingCBox = new ComboBox<>();
    ComboBox<String> numOfRatingCBox = new ComboBox<>();
    TextField ratingValue = new TextField();
    TextField numOfRatingValue = new TextField();

    // Year
    DatePicker fromMovieYear = new DatePicker();
    DatePicker toMovieYear = new DatePicker();

    // Tag
    TextArea tagText = new TextArea();
    ComboBox<String> tagWeightCbox = new ComboBox<>();
    TextField tagValueText = new TextField();

    TextArea queryTextArea = new TextArea();
    TextArea resultTextArea = new TextArea();

    // Logic, Buttons
    String searchCondition = "AND";
    ComboBox<String> logicCbox = new ComboBox<String>();
    Button executeButton = new Button();
    Button resetButton = new Button();

    // Panes
    VBox app;
    StackPane titlePane;
    GridPane topPane;
    HBox bottomBox;
    ScrollPane countryPane = new ScrollPane();
    ScrollPane genrePane  = new ScrollPane();
    ScrollPane locationPane = new ScrollPane();
    ScrollPane ratingPane = new ScrollPane();
    ScrollPane weightTagPane = new ScrollPane();
    ScrollPane tagPane = new ScrollPane();

    // UI design
    private void createTopPane() {
        topPane = new GridPane();
        topPane.setHgap(10);
        topPane.setVgap(10);
        topPane.setPadding(new Insets(0, 8, 0, 8));

        // three-level frame
        topPane.add(addTitle("Genres"), 0, 0);
        topPane.add(addTitle("Country"), 1, 0);
        topPane.add(addTitle("Filming Location"), 2, 0);
        topPane.add(addTitle("Critics' Rating"), 3, 0);
        topPane.add(addTitle("Movie Tag Values"), 4, 0);

        genreUI();
        countryUI();
        filmLocationUI();
        ratingUI();
        tagsUI();

        topPane.add(genrePane, 0, 1);
        topPane.add(countryPane, 1, 1);
        topPane.add(locationPane, 2, 1);
        topPane.add(ratingPane, 3, 1);
        topPane.add(weightTagPane, 4, 1);
    }

    private StackPane addTitle(String title) {
        StackPane Pane = new StackPane();
        Text text = new Text(title);
        text.setId("mulTitle");
        Pane.getChildren().add(text);
        Pane.setAlignment(Pos.CENTER);
        return Pane;
    }

    private void genreUI() {
        genrePane = new ScrollPane();
        genrePane.setMinSize(150, 250);
        genrePane.setMaxSize(150, 280);
        genrePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        genrePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        genrePane.setPadding(new Insets(2, 2, 2, 2));

        VBox box = new VBox();
        box.setPadding(new Insets(2, 2, 2, 5));
        box.setSpacing(5);
        genrePane.setContent(box);
    }

    private void countryUI() {
        countryPane = new ScrollPane();
        countryPane.setMinSize(150, 250);
        countryPane.setMaxSize(150, 280);
        countryPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        countryPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        countryPane.setPadding(new Insets(2, 2, 2, 5));

        VBox box = new VBox();
        box.setPadding(new Insets(2, 2, 2, 5));
        box.setSpacing(5);
        countryPane.setContent(box);
    }

    private void filmLocationUI() {
        locationPane = new ScrollPane();
        locationPane.setMinSize(150, 250);
        locationPane.setMaxSize(150, 280);
        locationPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        locationPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        locationPane.setPadding(new Insets(2, 2, 2, 5));

        VBox box = new VBox();
        box.setPadding(new Insets(2, 2, 2, 5));
        box.setSpacing(5);
        locationPane.setContent(box);
    }

    private void ratingUI() {
        ratingPane = new ScrollPane();
        ratingPane.setMinSize(220, 250);
        ratingPane.setMaxSize(220, 280);
        ratingPane.setPadding(new Insets(2, 2, 2, 2));

        // Rating
        Label ratingLabel = new Label("Rating:");
        ratingLabel.setMaxWidth(70);
        ratingLabel.setMinWidth(70);

        ratingCBox = new ComboBox<>();
        ratingCBox.setValue("=, <, >, ≥, ≤");
        ratingCBox.getItems().addAll("=", "<", ">", "≥", "≤");
        ratingCBox.setMaxWidth(120);

        Label valueLabel1 = new Label("Value:");
        valueLabel1.setMaxWidth(70);
        valueLabel1.setMinWidth(70);

        ratingValue = new TextField();
        ratingValue.setMaxWidth(120);

        // Num. of Ratings
        Label numOfRatingLabel = new Label("Num. Of Reviews:");
        numOfRatingLabel.setWrapText(true);
        numOfRatingLabel.setMaxWidth(70);
        numOfRatingLabel.setMinWidth(70);

        numOfRatingCBox = new ComboBox<>();
        numOfRatingCBox.setValue("=, <, >, ≥, ≤");
        numOfRatingCBox.getItems().addAll("=", "<", ">", "≥", "≤");
        numOfRatingCBox.setMaxWidth(120);

        Label valueLabel2 = new Label("Value:");
        valueLabel2.setMaxWidth(70);
        valueLabel2.setMinWidth(70);

        numOfRatingValue = new TextField();
        numOfRatingValue.setMaxWidth(120);

        // position
        GridPane ratingGridPane = new GridPane();
        ratingGridPane.setPadding(new Insets(5, 0, 2, 0));
        ratingGridPane.setVgap(2);
        ratingGridPane.setHgap(2);
        ratingGridPane.add(ratingLabel, 0, 0);
        ratingGridPane.add(ratingCBox, 1, 0);
        ratingGridPane.add(valueLabel1, 0, 1);
        ratingGridPane.add(ratingValue, 1, 1);

        GridPane numOfRatingGridPane = new GridPane();
        numOfRatingGridPane.setPadding(new Insets(15, 0, 2, 0));
        numOfRatingGridPane.setVgap(2);
        numOfRatingGridPane.setHgap(2);
        numOfRatingGridPane.add(numOfRatingLabel, 0, 0);
        numOfRatingGridPane.add(numOfRatingCBox, 1, 0);
        numOfRatingGridPane.add(valueLabel2, 0, 1);
        numOfRatingGridPane.add(numOfRatingValue, 1, 1);

        // Movie Year
        Label movieYearLabel = new Label("Movie Year");

        // From
        Label fromLable = new Label("From:");
        fromLable.setMaxWidth(60);
        fromLable.setMinWidth(60);

        fromMovieYear = new DatePicker();
        fromMovieYear.setPromptText("MM/DD/YYYY");
        fromMovieYear.setMaxWidth(120);

        // To
        Label toLable = new Label("To:");
        toLable.setMaxWidth(60);
        toLable.setMinWidth(60);

        toMovieYear = new DatePicker();
        toMovieYear.setPromptText("MM/DD/YYYY");
        toMovieYear.setMaxWidth(120);

        // Positioning From and To
        GridPane yearPane = new GridPane();
        yearPane.setPadding(new Insets(10, 5, 0, 5));
        yearPane.setHgap(5);
        yearPane.setVgap(5);
        yearPane.add(fromLable, 0, 0);
        yearPane.add(fromMovieYear,1 , 0);
        yearPane.add(toLable, 0, 1);
        yearPane.add(toMovieYear, 1, 1);

        VBox ratingVbox = new VBox();
        ratingVbox.setSpacing(5);
        ratingVbox.setPadding(new Insets(0, 5, 0, 5));
        ratingVbox.getChildren().addAll(ratingGridPane, numOfRatingGridPane);

        VBox yearVbox = new VBox();
        yearVbox.setSpacing(5);
        yearVbox.setPadding(new Insets(0, 5, 0, 5));
        yearVbox.getChildren().addAll(movieYearLabel, yearPane);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(ratingVbox, yearVbox);
        ratingPane.setContent(vbox);

    }

    private void tagsUI() {
        // tag pane
        tagPane = new ScrollPane();
        tagPane.setMinSize(240, 190);
        tagPane.setMaxSize(240, 190);
        tagPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tagPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tagPane.setPadding(new Insets(10, 2, 2, 5));
        VBox tagBox = new VBox();
        tagBox.setSpacing(5);
        tagBox.setPadding(new Insets(5, 5, 0 , 5));
        tagPane.setContent(tagBox);

        Label userRatingLabel = new Label("Tag Weight:");
        userRatingLabel.setWrapText(true);
        userRatingLabel.setMaxWidth(60);
        userRatingLabel.setMinWidth(60);

        tagWeightCbox = new ComboBox<>();
        tagWeightCbox.setValue("=, <, >, ≥, ≤");
        tagWeightCbox.getItems().addAll("=", "<", ">", "≥", "≤");
        tagWeightCbox.setMinWidth(150);

        Label valueLabel = new Label("Value:");
        valueLabel.setMaxWidth(60);
        valueLabel.setMinWidth(60);

        tagValueText = new TextField();
        tagValueText.setMaxWidth(150);

        // position
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 0, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(2);
        gridPane.add(userRatingLabel, 0, 0);
        gridPane.add(tagWeightCbox, 1, 0);
        gridPane.add(valueLabel, 0, 1);
        gridPane.add(tagValueText, 1, 1);

        VBox box = new VBox();
        box.getChildren().addAll(tagPane, gridPane);

        weightTagPane = new ScrollPane();
        weightTagPane.setMinSize(250, 280);
        weightTagPane.setMaxSize(250, 280);
        weightTagPane.setPadding(new Insets(2, 5, 2, 5));
        weightTagPane.setContent(box);
    }

    private void createBottomBox() {

        queryTextArea = new TextArea();
        queryTextArea.setPromptText("Show Query here");
        queryTextArea.setMinWidth(400);
        queryTextArea.setMaxWidth(400);
        queryTextArea.setMinHeight(200);
        queryTextArea.setMaxHeight(200);
        queryTextArea.setEditable(false);
        queryTextArea.setWrapText(true);

        VBox searchVbox = new VBox();
        searchVbox.setSpacing(10);
        searchVbox.getChildren().addAll(queryTextArea);

        logicCbox = new ComboBox<>();
        logicCbox.setValue("AND");
        logicCbox.getItems().addAll("AND", "OR");
        logicCbox.setMinWidth(150);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setSpacing(10);
        executeButton = new Button("EXECUTE QUERY");
        executeButton.setWrapText(true);
        executeButton.setMinWidth(150);
        resetButton = new Button("RESET QUERY");
        resetButton.setWrapText(true);
        resetButton.setMinWidth(150);
        buttonBox.getChildren().addAll(logicCbox, executeButton, resetButton);

        VBox allButton = new VBox();
        allButton.setSpacing(5);
        allButton.setPadding(new Insets(5, 5, 0, 5));
        allButton.getChildren().add(buttonBox);

        VBox resultVbox = new VBox();
        resultVbox.setSpacing(10);

        resultTextArea = new TextArea();
        resultTextArea.setPromptText("Show result here");
        resultTextArea.setMinWidth(380);
        resultTextArea.setMaxWidth(380);
        resultTextArea.setMinHeight(200);
        resultTextArea.setMaxHeight(200);

        resultVbox.getChildren().add(resultTextArea);

        bottomBox = new HBox();
        bottomBox.setPadding(new Insets(0, 10, 0, 10));
        bottomBox.setSpacing(10);
        resultTextArea.setEditable(false);

        bottomBox.getChildren().addAll(searchVbox, allButton, resultVbox);
    }

    // Main function
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Connection connect = DButil.openConnection();
        initApp(window, connect);

    }

    private void initApp(Stage window, Connection connect) throws Exception {
        // Combine UI Interface
        window.setTitle("DBHW_TingtingBao");
        app = new VBox(5);
        app.setPadding(new Insets(5, 20, 0, 20));
        app.setSpacing(15);
        Text movie = new Text("MOVIE APPLICATION");
        movie.setId("title");
        titlePane = new StackPane();
        titlePane.getChildren().add(movie);
        createTopPane();
        createBottomBox();
        app.getChildren().addAll(titlePane, topPane, bottomBox);


        btnFunctionImplement(connect);
        displayGenres(connect);

        Scene scene = new Scene(app, 1080, 720);
        //scene.getStylesheets().add("movie.css");
        window.setScene(scene);
        window.show();
    }

    // Query Function Implement
    public void displayGenres(Connection connect) throws SQLException {
        VBox box = new VBox();
        box.setPadding(new Insets(2, 2, 2, 5));
        box.setSpacing(5);

        ResultSet genres = DButil.fetchAllGenres(connect);
        Set<String> newSelectedGenres = new HashSet<>();
        while (genres.next()) {
            String genre = genres.getString("GENRES");
            CheckBox checkBox = new CheckBox(genre);
            if (selectedGenres.contains(genre)) {
                checkBox.setSelected(true);
                newSelectedGenres.add(genre);
            }
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        selectedGenres.add(genre);
                    } else {
                        selectedGenres.remove(genre);
                    }
                    renderCountryPane(connect);
                    renderLocationPane(connect);
                    renderTagPane(connect);
                }
            });
            box.getChildren().add(checkBox);
        }
        selectedCountries = newSelectedGenres;
        genrePane.setContent(box);
    }

    private void renderCountryPane(Connection connect) {
        VBox content = new VBox();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setSpacing(8);
        if (!selectedGenres.isEmpty()) {
            try {
                ResultSet countryResult = DButil.fetchCountryByGenres(connect, selectedGenres, searchCondition);
                Set<String> newSelectedCountries = new HashSet<>();
                while (countryResult.next()) {
                    String country = countryResult.getString("COUNTRY");
                    CheckBox checkBox = new CheckBox(country);
                    if (selectedCountries.contains(country)) {
                        checkBox.setSelected(true);
                        newSelectedCountries.add(country);
                    }
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (newValue) {
                                selectedCountries.add(country);
                            } else {
                                selectedCountries.remove(country);
                            }
                            renderLocationPane(connect);
                            renderTagPane(connect);
                        }
                    });
                    content.getChildren().add(checkBox);
                }
                selectedCountries = newSelectedCountries;
            }catch (SQLException e) {
                System.err.println("Failed to renderCountryPane" + e.getMessage());
            }
        }
        countryPane.setContent(content);
    }

    private void renderLocationPane(Connection connect) {
        VBox content = new VBox();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setSpacing(8);
        if (!selectedCountries.isEmpty()) {
            try {
                ResultSet locationResult = DButil.fetchLocationByGenreAndCountry(connect, selectedGenres, selectedCountries, searchCondition);
                Set<String> newSelectedLocations = new HashSet<>();
                while (locationResult.next()) {
                    String location = locationResult.getString("LOCATION1");
                    CheckBox checkBox = new CheckBox(location);
                    if (selectedLocations.contains(location)) {
                        checkBox.setSelected(true);
                        newSelectedLocations.add(location);
                    }
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (newValue) {
                                selectedLocations.add(location);
                            } else {
                                selectedLocations.remove(location);
                            }
                            renderTagPane(connect);
                        }
                    });
                    content.getChildren().add(checkBox);
                }
                selectedLocations = newSelectedLocations;
            } catch (SQLException e) {
                System.err.println("Failed to render LocationPane: " + e.getMessage());
            }
        }
        locationPane.setContent(content);
    }



    private void renderTagPane(Connection connect) {
        VBox content = new VBox();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setSpacing(8);
        try {
            ResultSet tagResult = generateQueryTag(connect);
            Set<String> newSelectedTags = new HashSet<>();
            while (tagResult.next()) {
                String tag = tagResult.getString("TAGTEXT");
                CheckBox checkBox = new CheckBox(tag);
                if (selectedTags.contains(tag)) {
                    checkBox.setSelected(true);
                    newSelectedTags.add(tag);
                }
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            selectedTags.add(tag);
                        } else {
                            selectedTags.remove(tag);
                        }
                    }
                });
                content.getChildren().add(checkBox);
            }
            selectedTags = newSelectedTags;
        } catch (SQLException e) {
                System.err.println("Failed to render TagPane: " + e.getMessage());
        }
        tagPane.setContent(content);
    }

    private void btnFunctionImplement(Connection connect) {
        logicCbox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String oldString, String newString) {
                if (newString == null) {
                    System.out.println("Reset Triggered");
                } else {
                    selectLogic(connect, newString);
                }
            }
        });

        executeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (selectedGenres.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning Dialog");
                        alert.setContentText("Please select genres to execute query");
                        alert.showAndWait();
                    } else {
                        String query = collectAllQueryRequest();
                        System.out.println(query);
                        printResultText(executeQuery(connect, query));
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Please check whether rating value, user ID and date are correct or not");
                    alert.showAndWait();
                } catch (SQLException e) {
                    System.err.println("Error occurs when executing query: " + e.getMessage());
                }
            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    selectedCountries.clear();
                    selectedGenres.clear();
                    for (Node checkBox : ((VBox) genrePane.getContent()).getChildren()) {
                        ((CheckBox) checkBox).setSelected(false);
                    }

                    ratingCBox.getSelectionModel().clearSelection();
                    ratingCBox.setValue("=, <, >, ≥, ≤");
                    ratingValue.clear();
                    numOfRatingCBox.getSelectionModel().clearSelection();
                    numOfRatingCBox.setValue("=, <, >, ≥, ≤");
                    numOfRatingValue.clear();
                    fromMovieYear.setValue(null);
                    fromMovieYear.setPromptText("MM/DD/YYYY");
                    toMovieYear.setValue(null);
                    fromMovieYear.setPromptText("MM/DD/YYYY");

                    tagText.clear();
                    tagWeightCbox.getSelectionModel().clearSelection();
                    tagWeightCbox.setValue("=, <, >, ≥, ≤");
                    tagValueText.clear();

                    logicCbox.getSelectionModel().clearSelection();
                    logicCbox.setValue("Select AND, OR between attributes");

                    queryTextArea.clear();
                    resultTextArea.clear();

                    renderCountryPane(connect);

                } catch (Exception e) {
                    System.err.println("Error occurs when reset application: " + e.getMessage());
                }
            }
        });
    }



    private void selectLogic(Connection connect, String condition) {
        searchCondition = condition;
        System.out.println("TEST" + searchCondition);
        try {
            displayGenres(connect);
            renderCountryPane(connect);
            renderLocationPane(connect);
        } catch (SQLException e) {
            System.err.println("Errors occurs when perform Logic operation: " + e.getMessage());
        }
    }

    /*
     * Collect Query movie country
     */
    private String collectQueryCountries() {
        StringBuilder sb = new StringBuilder();
        String prefix = " (";
        for (String country : selectedCountries) {
            if (sb.length() == 0) {
                sb.append(prefix + "C.country = '" + country + "' ");
            } else {
                // Assume one movie can be only produced by one country
                sb.append(searchCondition + prefix + "C.country = '" + country + "' ");
            }
            prefix = " ";
        }
        if (sb.length() != 0) {
            sb.append(")\n");
        }
        return sb.toString();
    }

    /*
     * Collect Query filming location country
     */
    private String collectQueryFilming() {
        StringBuilder sb = new StringBuilder();
        String prefix = " (";
        for (String location: selectedLocations) {
            if (sb.length() == 0) {
                sb.append(prefix + "L.LOC Like '%" + location + "%' ");
            }else {
                sb.append(searchCondition + prefix + "L.LOC LIKE '%" + location + "%' ");
            }
            prefix = " ";
        }
        if (sb.length() != 0) {
            sb.append(")\n");
        }
        return sb.toString();
    }

    /*
     * Collect Query Genre by referring to checkBox
     */
    private String collectQueryGenres() {
        StringBuilder sb = new StringBuilder();
        String prefix = " (";
        for (String genre : selectedGenres) {
            if (sb.length() == 0) {
                sb.append("G.Genre LIKE '%" + genre + "%' ");
            } else {
                sb.append(searchCondition + " G.Genre LIKE '%" + genre + "%' ");
            }
        }
        if (sb.length() != 0) {
            sb.append("\n");
        }
        return sb.toString();
    }

    private void helperForWhere(StringBuilder where, String sql) {
        where.append("AND (\n" + sql + ")");
    }

    private ResultSet generateQueryTag(Connection connect) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT tagtext " +
                "FROM movies M, movie_tags MT, tags T, " +
                "(SELECT DISTINCT mid, LISTAGG(location1, ',') WITHIN GROUP (ORDER BY location1) AS LOC\n" +
                "FROM (SELECT DISTINCT location1, mid FROM movie_locations) LOC2\n" +
                "GROUP BY mid) L, movie_countries C,\n" +
                "(SELECT mid, LISTAGG(genres, ', ') WITHIN GROUP (ORDER BY genres) AS Genre\n" +
                "FROM movie_genres GROUP BY mid) G\n" +
                "WHERE M.mid = C.mid AND M.mid = G.mid AND M.mid = L.mid AND MT.tid = T.tid AND M.mid = MT.mid ");

        if (selectedGenres.size() != 0) {
            helperForWhere(sb, collectQueryGenres());
        }

        if (selectedCountries.size() != 0 ) {
            helperForWhere(sb, collectQueryCountries());
        }

        if (selectedLocations.size() != 0) {
            helperForWhere(sb, collectQueryFilming());
        }

        System.out.println(String.format("Fetch QueryTag: %s", sb.toString()));
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sb.toString());
    }

    private String collectAllQueryRequest() {

        StringBuilder select = new StringBuilder();
        StringBuilder from = new StringBuilder();
        StringBuilder where = new StringBuilder();

        // set up select part (use SQL to calculate average)
        select.append("SELECT DISTINCT title, G.genre as genre, myear, country, L.LOC AS FilmLoction,\n" +
                "TRUNC((AllCriticRating+TopCriticrating+Audiencerating)/3, 2) AS AvgRating,\n" +
                "TRUNC((AllCriticNoReview+TopCriticNoReview+AudienceNorating)/3, 2) AS AvgReviews\n");

        // set up from part (Location)
        from.append("FROM movies M, " +
                "(SELECT DISTINCT mid, LISTAGG(location1, ',') WITHIN GROUP (ORDER BY location1) AS LOC\n" +
                "FROM (SELECT DISTINCT location1, mid FROM movie_locations) LOC2\n" +
                "GROUP BY mid) L, movie_countries C,\n");

        // set up from part (Genre)
        from.append("(SELECT mid, LISTAGG(genres, ', ') WITHIN GROUP (ORDER BY genres) AS Genre\n");
        from.append("FROM movie_genres GROUP BY mid) G\n");

        // set up where part
        where.append("WHERE M.mid = C.mid AND M.mid = G.mid AND M.mid = L.mid ");

        if (selectedGenres.size() != 0) {
            helperForWhere(where, collectQueryGenres());
        }

        if (selectedCountries.size() != 0 ) {
            helperForWhere(where, collectQueryCountries());
        }

        if (selectedLocations.size() != 0) {
            helperForWhere(where, collectQueryFilming());
        }

        // add critic rating
        if (!ratingValue.getText().isEmpty()) {
            String val = ratingValue.getText();
            where.append(" AND M.rtAllCriticsRating " + ratingCBox.getValue() + val + " ");
        }

        // add number of reviews
        if (!numOfRatingValue.getText().isEmpty()) {
            String val = numOfRatingValue.getText();
            where.append(" AND M.rtAllCriticsNumReviews " + numOfRatingCBox.getValue() + val + " ");
        }

        // add movieYear
        if (fromMovieYear.getValue() != null || toMovieYear.getValue() != null) {
            if (fromMovieYear.getValue() != null) {
                where.append(" AND M.myear >= " + fromMovieYear.getValue().getYear() + " ");
            }
            if (toMovieYear.getValue() != null) {
                where.append(" AND M.myear <=  " + toMovieYear.getValue().getYear() + " ");
            }
        }

        // add Tag Weight
        /*if (!tagValueText.getText().isEmpty()) {
            String val = tagValueText.getText();
            where.append(" AND ");
        }*/

        return select.toString() + from.toString() + where.toString();
    }

    //private String

    /*
     *  Query Execute function
     */
    private ResultSet executeQuery(Connection connect, String query) throws SQLException {
        Statement prestate = connect.createStatement();
        queryTextArea.clear();
        queryTextArea.setText(query);
        ResultSet result = prestate.executeQuery(query);
        return result;
    }


    private void printResultText(ResultSet resultSet) throws SQLException {
        if (!resultSet.isBeforeFirst()) {
            resultTextArea.setText("No Data Available");
        } else {
            ResultSetMetaData meta = resultSet.getMetaData();
            StringBuilder sb = new StringBuilder();
            while (resultSet.next()) {
                for (int col = 1; col <= meta.getColumnCount(); col++) {
                    sb.append(resultSet.getString(col));
                    if (col != meta.getColumnCount()) {
                        sb.append("\t ");
                    }
                }
                sb.append("\n");
            }
            resultTextArea.setText(sb.toString());
        }
    }
}