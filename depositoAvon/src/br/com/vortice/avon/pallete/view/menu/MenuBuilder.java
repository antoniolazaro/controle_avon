package br.com.vortice.avon.pallete.view.menu;

import br.com.vortice.avon.pallete.view.menu.menuitem.AssociarProdutoMenuItem;
import br.com.vortice.avon.pallete.view.menu.menuitem.PosicaoMenuItem;
import br.com.vortice.avon.pallete.view.menu.menuitem.ProdutoMenuItem;
import br.com.vortice.avon.pallete.view.menu.menuitem.VisualizarProdutosPalleteMenuItem;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuBuilder {
	
	private BorderPane root;
	private Stage primaryStage;
	
	public MenuBuilder(Stage primaryStage,BorderPane root) {
		this.primaryStage = primaryStage;
		this.root = root;
	}
	
	public void buildMenu(){
		MenuBar menuBar = new MenuBar();
	    menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
	    menuBar.useSystemMenuBarProperty().set(true);

	    root.setTop(menuBar);
	    
	    Menu produtoMenu = new Menu("Produto");
	    MenuItem manterProdutoMenuItem = new MenuItem("Manter Produto");
	    manterProdutoMenuItem.setOnAction(actionEvent -> new ProdutoMenuItem().buildMenuItem(primaryStage));
	    produtoMenu.getItems().addAll(manterProdutoMenuItem);
	    
	    Menu posicaoMenu = new Menu("Posição");
	    MenuItem manterPosicaoMenuItem = new MenuItem("Manter Posição");
	    manterPosicaoMenuItem.setOnAction(actionEvent -> new PosicaoMenuItem().buildMenuItem(primaryStage));
	    posicaoMenu.getItems().addAll(manterPosicaoMenuItem);
	    
	    Menu associarProduto = new Menu("Associar produto");
	    MenuItem entradaProdutoItem = new MenuItem("Entrada de produto");
	    entradaProdutoItem.setOnAction(actionEvent -> new AssociarProdutoMenuItem().buildMenuItem(primaryStage));
	    associarProduto.getItems().addAll(entradaProdutoItem);
	    
	    MenuItem visualizarProdutosPalleteItem = new MenuItem("Visualizar status pallets.");
	    visualizarProdutosPalleteItem.setOnAction(actionEvent -> new VisualizarProdutosPalleteMenuItem().buildMenuItem(primaryStage));
	    associarProduto.getItems().addAll(visualizarProdutosPalleteItem);
	    
	    Menu sair = new Menu("Sair");
	    sair.setOnAction(actionEvent -> Platform.exit());
	    
	    menuBar.getMenus().addAll(produtoMenu,posicaoMenu,associarProduto,sair);
	}
	
	

}
