package br.com.vortice.avon.pallete.view.menu.menuitem;

import java.util.List;

import br.com.vortice.avon.pallete.business.PalleteBusiness;
import br.com.vortice.avon.pallete.model.PalleteModel;
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

public class PosicaoMenuItem {
	
	private PalleteBusiness palleteBusiness;
	private ObservableList<PalleteModel> conteudoTabela;
	public PosicaoMenuItem() {
		palleteBusiness = new PalleteBusiness();
	}
	
	private TableView createTableView(){
		TableView table = new TableView();

        table.setEditable(true);
        
        TableColumn firstNameCol = new TableColumn("Código");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, String>("codigo"));

        TableColumn lastNameCol = new TableColumn("Identificação");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, String>("identificacao"));

        List<PalleteModel> listaPallete = null;
        try{
        	listaPallete = palleteBusiness.selectAll();
	        
        }catch(Exception ex){
        	ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
        }
        conteudoTabela = FXCollections.observableArrayList(listaPallete);
        table.setItems(conteudoTabela);
        table.getColumns().addAll(firstNameCol, lastNameCol);
        
        return table;
	}
	
	private HBox createAdicionarPalleteSection(){
		final HBox hb = new HBox();
        
        final TextField codigo = new TextField();
        codigo.setPromptText("Código");
        codigo.setMaxWidth(100);
        
        final TextField identificacao = new TextField();
        identificacao.setPromptText("Identificação");
        identificacao.setMaxWidth(200);
        
        final Button addButton = new Button("Adicionar Pallet");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
	            	PalleteModel palleteModel = new PalleteModel(Long.valueOf(codigo.getText()),identificacao.getText());
	            	palleteBusiness.insert(palleteModel);
	            	conteudoTabela.add(palleteModel);
	                codigo.clear();
	                identificacao.clear();
	                ShowAlertUtil.exibirMensagemInfo("Ação realizada com sucesso!");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        hb.getChildren().addAll(codigo, identificacao,addButton);
        hb.setSpacing(2);
        return hb;
	}
	
	private HBox createRemoverPalleteSection(){
		final HBox hb = new HBox();
        
        final TextField codigo = new TextField();
        codigo.setPromptText("Código");
        codigo.setMaxWidth(100);
        
        final Button addButton = new Button("Remover Pallet");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
	            	PalleteModel palleteModel = new PalleteModel(Long.valueOf(codigo.getText()));
	            	palleteBusiness.delete(palleteModel);
	            	conteudoTabela.remove(palleteModel);
	                codigo.clear();
	                ShowAlertUtil.exibirMensagemInfo("Ação realizada com sucesso!");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        hb.getChildren().addAll(codigo,addButton);
        hb.setSpacing(2);
        return hb;
	}
	
	private HBox createLiberarPalleteSection(){
		final HBox hb = new HBox();
        
        final TextField codigo = new TextField();
        codigo.setPromptText("Código");
        codigo.setMaxWidth(100);
        
        final Button addButton = new Button("Liberar Pallet");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
	            	PalleteModel palleteModel = new PalleteModel(Long.valueOf(codigo.getText()));
	            	palleteBusiness.deleteProdutosFromPallete(palleteModel);
	            	conteudoTabela.remove(palleteModel);
	                codigo.clear();
	                ShowAlertUtil.exibirMensagemInfo("Ação realizada com sucesso!");
            	}catch(Exception ex){
                	ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        hb.getChildren().addAll(codigo,addButton);
        hb.setSpacing(2);
        return hb;
	}

	public void buildMenuItem(Stage primaryStage){
		Scene scene = new Scene(new Group());
	    final Label label = new Label("Lista Pallets");
        label.setFont(new Font("Arial", 20));

        TableView table = createTableView();
        HBox adicionarPalleteSection = createAdicionarPalleteSection();
        HBox removerPalleteSection = createRemoverPalleteSection();
        HBox liberarPalleteSection = createLiberarPalleteSection();
        
        final VBox vbox = new VBox();
        vbox.setSpacing(4);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table,adicionarPalleteSection,removerPalleteSection,liberarPalleteSection);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
