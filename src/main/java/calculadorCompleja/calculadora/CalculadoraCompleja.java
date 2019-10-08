package calculadorCompleja.calculadora;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {
	
	//VIEW
	
	private TextField numero1Text;
	private TextField numeroImaginario1Text;
	private TextField numero2Text;
	private TextField numeroImaginario2Text;
	private TextField resultado1Text;
	private TextField resultado2Text;
	private Label operador1Label;
	private Label operador2Label;
	private Label operador3Label;
	private ComboBox<String> operadorCombo;
	private Separator separadorSeparator;
	
	//MODEL
	
	private Complejo operadorComplejo1 = new Complejo();
	private Complejo operadorComplejo2 = new Complejo();
	private Complejo resultadoComplejo = new Complejo();
	private StringProperty operacion = new SimpleStringProperty();

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		numero1Text = new TextField();
		numero1Text.setPrefColumnCount(4);

		numero2Text = new TextField();
		numero2Text.setPrefColumnCount(4);
		
		operador1Label = new Label("+");
		operador2Label = new Label("+");
		operador3Label = new Label("+");

		numeroImaginario1Text = new TextField();
		numeroImaginario1Text.setPrefColumnCount(4);

		numeroImaginario2Text = new TextField();
		numeroImaginario2Text.setPrefColumnCount(4);

		resultado1Text = new TextField();
		resultado1Text.setPrefColumnCount(4);
		resultado1Text.setDisable(true);

		resultado2Text = new TextField();
		resultado2Text.setPrefColumnCount(4);
		resultado2Text.setDisable(true);

		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");

		separadorSeparator = new Separator();
		separadorSeparator.setMaxWidth(150);

		HBox primerNumero = new HBox(5, numero1Text,operador1Label,numeroImaginario1Text, new Label("i"));
		primerNumero.setAlignment(Pos.CENTER);
		
		HBox segundoNumero = new HBox(5, numero2Text,operador2Label,numeroImaginario2Text, new Label("i"));
		segundoNumero.setAlignment(Pos.CENTER);

		HBox resultados = new HBox(5, resultado1Text,operador3Label, resultado2Text,new Label("i"));
		resultados.setAlignment(Pos.CENTER);

		VBox combo = new VBox(5, operadorCombo);
		combo.setAlignment(Pos.CENTER);
		
		VBox panel = new VBox(5,primerNumero,segundoNumero,separadorSeparator,resultados);
		panel.setAlignment(Pos.CENTER);

		HBox root = new HBox(5,combo,panel);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Calculador Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//BINDEOS
		
		numero1Text.textProperty().bindBidirectional(operadorComplejo1.realProperty(), new NumberStringConverter());
		numeroImaginario1Text.textProperty().bindBidirectional(operadorComplejo1.imaginarioProperty(), new NumberStringConverter());
		numero2Text.textProperty().bindBidirectional(operadorComplejo2.realProperty(), new NumberStringConverter());
		numeroImaginario2Text.textProperty().bindBidirectional(operadorComplejo2.imaginarioProperty(), new NumberStringConverter());
		resultado1Text.textProperty().bindBidirectional(resultadoComplejo.realProperty(), new NumberStringConverter());
		resultado2Text.textProperty().bindBidirectional(resultadoComplejo.imaginarioProperty(), new NumberStringConverter());
		operacion.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		operacion.addListener((o, ov , nv) -> onOperacionChange(nv));
		
		operadorCombo.getSelectionModel().selectFirst();
		
		
	}

	private void onOperacionChange(String nv) {
		
		switch(nv) {
		case "+": 
			operador1Label.setText("+");
			operador2Label.setText("+");
			operador3Label.setText("+");
			resultadoComplejo.realProperty().bind(operadorComplejo1.realProperty().add(operadorComplejo2.realProperty()));
			resultadoComplejo.imaginarioProperty().bind(operadorComplejo1.imaginarioProperty().add(operadorComplejo2.imaginarioProperty()));
			break;
		case "-": 
			operador1Label.setText("-");
			operador2Label.setText("-");
			operador3Label.setText("-");
			resultadoComplejo.realProperty().bind(operadorComplejo1.realProperty().subtract(operadorComplejo2.realProperty()));
			resultadoComplejo.imaginarioProperty().bind(operadorComplejo1.imaginarioProperty().subtract(operadorComplejo2.imaginarioProperty()));
		break;
		case "*": 
			operador1Label.setText("*");
			operador2Label.setText("*");
			operador3Label.setText("*");
			resultadoComplejo.realProperty().bind(operadorComplejo1.realProperty()
					.multiply(operadorComplejo2.realProperty())
					.subtract(operadorComplejo1.imaginarioProperty()
					.multiply(operadorComplejo2.imaginarioProperty())));
			
			resultadoComplejo.imaginarioProperty().bind(operadorComplejo1.realProperty()
					.multiply(operadorComplejo2.imaginarioProperty())
					.subtract(operadorComplejo2.realProperty()
					.multiply(operadorComplejo1.imaginarioProperty())));
			
			break;
		case "/": 
			operador1Label.setText("/");
			operador2Label.setText("/");
			operador3Label.setText("/");
			
			resultadoComplejo.realProperty().bind(
					operadorComplejo1.realProperty().multiply(operadorComplejo2.realProperty())
					.add(operadorComplejo1.imaginarioProperty()).multiply(operadorComplejo2.
					imaginarioProperty()).divide(operadorComplejo2.realProperty().
					multiply(operadorComplejo2.realProperty()).add(operadorComplejo2.imaginarioProperty().
					multiply(operadorComplejo2.imaginarioProperty()))));
			
			resultadoComplejo.imaginarioProperty().bind(
					operadorComplejo1.imaginarioProperty().multiply(operadorComplejo2.realProperty())
					.subtract(operadorComplejo1.realProperty()).multiply(operadorComplejo2.
					imaginarioProperty()).divide(operadorComplejo2.realProperty().
					multiply(operadorComplejo2.realProperty()).add(operadorComplejo2.imaginarioProperty().
					multiply(operadorComplejo2.imaginarioProperty()))));
			break;
		}	
		
	}

}
