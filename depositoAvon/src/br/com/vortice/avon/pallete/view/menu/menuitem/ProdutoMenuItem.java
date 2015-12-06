package br.com.vortice.avon.pallete.view.menu.menuitem;

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

public class ProdutoMenuItem {
	
	private ProdutoBusiness produtoBusiness;
	private ObservableList<ProdutoModel> conteudoTabela;
	public ProdutoMenuItem() {
		produtoBusiness = new ProdutoBusiness();
	}
	
	private TableView createTableView(){
		TableView table = new TableView();

        table.setEditable(true);
        
        TableColumn codigoCol = new TableColumn("Código");
        codigoCol.setMinWidth(20);
        codigoCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, String>("codigo"));
        
        TableColumn nomeCol = new TableColumn("Nome");
        nomeCol.setMinWidth(20);
        nomeCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, String>("nome"));

        TableColumn codigoBarrasCol = new TableColumn("Código de Barras");
        codigoBarrasCol.setMinWidth(40);
        codigoBarrasCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, String>("codigoBarras"));

        TableColumn fscCodeCol = new TableColumn("Fsc Code");
        fscCodeCol.setMinWidth(40);
        fscCodeCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, String>("fscCode"));
        
        TableColumn descricaoCol = new TableColumn("Descrição");
        descricaoCol.setMinWidth(60);
        descricaoCol.setCellValueFactory(new PropertyValueFactory<ProdutoModel, String>("descricao"));
        
        List<ProdutoModel> listaProdutos = null;
        try{
        	listaProdutos = produtoBusiness.selectAll();
	        
        }catch(Exception ex){
        	ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
        }
        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        table.setItems(conteudoTabela);
        table.getColumns().addAll(codigoCol, codigoBarrasCol,fscCodeCol,descricaoCol);
        
        return table;
	}
	
	private HBox createAdicionarPalleteSection(){
		final HBox hb = new HBox();
        
        final TextField codigo = new TextField();
        codigo.setPromptText("Código");
        codigo.setMaxWidth(100);

        final TextField nome = new TextField();
        nome.setPromptText("Nome");
        nome.setMaxWidth(100);
        
        final TextField codigoBarras = new TextField();
        codigoBarras.setPromptText("Código de Barras");
        codigoBarras.setMaxWidth(200);
        
        final TextField fscCode = new TextField();
        fscCode.setPromptText("FSC Code");
        fscCode.setMaxWidth(200);
        
        final TextField descricao = new TextField();
        descricao.setPromptText("Descrição");
        descricao.setMaxWidth(200);
        
        final Button addButton = new Button("Adicionar Produto");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
	            	ProdutoModel produtoModel = new ProdutoModel(Long.valueOf(codigo.getText()),codigoBarras.getText(),fscCode.getText(),descricao.getText());
	            	produtoBusiness.insert(produtoModel);
	            	conteudoTabela.add(produtoModel);
	                codigo.clear();
	                codigoBarras.clear();
	                fscCode.clear();
	                descricao.clear();
	                ShowAlertUtil.exibirMensagemInfo("Ação realizada com sucesso!");
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        hb.getChildren().addAll(codigo,nome,codigoBarras,fscCode,descricao,addButton);
        hb.setSpacing(5);
        return hb;
	}
	
	private HBox createRemoverPalleteSection(){
		final HBox hb = new HBox();
        
        final TextField codigo = new TextField();
        codigo.setPromptText("Código");
        codigo.setMaxWidth(100);
        
        final Button addButton = new Button("Remover Produto");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		ProdutoModel produtoModel = new ProdutoModel(Long.valueOf(codigo.getText()));
	            	produtoBusiness.delete(produtoModel);
	            	conteudoTabela.remove(produtoModel);
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
        
        final Button addButton = new Button("Limpar produto de todas posições");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		ProdutoModel produtoModel = new ProdutoModel(Long.valueOf(codigo.getText()));
            		produtoBusiness.deleteProdutosFromPallete(produtoModel);
	            	conteudoTabela.remove(produtoModel);
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
	    final Label label = new Label("Lista Produtos");
        label.setFont(new Font("Arial", 20));

        TableView table = createTableView();
        HBox adicionarPalleteSection = createAdicionarPalleteSection();
        HBox removerPalleteSection = createRemoverPalleteSection();
        HBox liberarPalleteSection = createLiberarPalleteSection();
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table,adicionarPalleteSection,removerPalleteSection,liberarPalleteSection);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
