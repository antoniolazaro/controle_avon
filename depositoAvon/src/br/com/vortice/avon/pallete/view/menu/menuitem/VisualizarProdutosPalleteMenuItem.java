package br.com.vortice.avon.pallete.view.menu.menuitem;

import java.util.ArrayList;
import java.util.List;

import br.com.vortice.avon.pallete.business.ProdutoBusiness;
import br.com.vortice.avon.pallete.model.PalleteModel;
import br.com.vortice.avon.pallete.model.ProdutoModel;
import br.com.vortice.avon.pallete.view.ShowAlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class VisualizarProdutosPalleteMenuItem {
	
	private ProdutoBusiness produtoBusiness;
	private ObservableList<ProdutoModel> conteudoTabela;
	public VisualizarProdutosPalleteMenuItem() {
		produtoBusiness = new ProdutoBusiness();
	}
	
	private TableView createTableView(){
		TableView table = new TableView();

        table.setEditable(true);
        
        TableColumn codigoCol = new TableColumn("Quantidade");
        codigoCol.setMinWidth(20);
        codigoCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, String>("quantidade"));

        TableColumn codigoBarrasCol = new TableColumn("Código de Barras");
        codigoBarrasCol.setMinWidth(40);
        codigoBarrasCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, String>("codigoBarras"));

        TableColumn fscCodeCol = new TableColumn("Fsc Code");
        fscCodeCol.setMinWidth(40);
        fscCodeCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, String>("fscCode"));
        
        TableColumn descricaoCol = new TableColumn("Descrição");
        descricaoCol.setMinWidth(60);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, String>("descricao"));
        
        List<ProdutoModel> listaProdutos = new ArrayList<ProdutoModel>();
        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        table.setItems(conteudoTabela);
        table.getColumns().addAll(codigoBarrasCol,fscCodeCol,descricaoCol,codigoCol);
        
        return table;
	}
	
	private HBox createAdicionarPalleteSection(){
		final HBox hb = new HBox();
        
        final TextField codigoBarras = new TextField();
        codigoBarras.setPromptText("Identificação Pallete");
        codigoBarras.setMaxWidth(200);
        
        final Button addButton = new Button("Pesquisar pela identificação da Posição");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
	            	PalleteModel palleteModel = new PalleteModel();
	            	palleteModel.setIdentificacao(codigoBarras.getText());
	            	List<ProdutoModel> listaProdutos = produtoBusiness.selectListProductByPalleteId(palleteModel);
	            	if(!listaProdutos.isEmpty()){
		            	conteudoTabela.clear();
		            	conteudoTabela.addAll(listaProdutos);
	            	}else{
	            		ShowAlertUtil.exibirMensagemInfo("Posição vazio.");
	            	}
	            	codigoBarras.clear();
	                
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        hb.getChildren().addAll(codigoBarras,addButton);
        hb.setSpacing(5);
        return hb;
	}
	
	
	public void buildMenuItem(Stage primaryStage){
		Scene scene = new Scene(new Group());
	    final Label label = new Label("Lista Produtos");
        label.setFont(new Font("Arial", 20));

        TableView table = createTableView();
        HBox adicionarPalleteSection = createAdicionarPalleteSection();
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table,adicionarPalleteSection);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
