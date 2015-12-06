package br.com.vortice.avon.pallete.view.menu.menuitem;

import java.util.List;

import br.com.vortice.avon.pallete.business.PalleteBusiness;
import br.com.vortice.avon.pallete.model.PalleteModel;
import br.com.vortice.avon.pallete.view.ShowAlertUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VisualizarProdutosPalleteMenuItem {
	
	private PalleteBusiness palleteBusiness;
	private ObservableList<PalleteModel> conteudoTabela;
	
	public VisualizarProdutosPalleteMenuItem() {
		palleteBusiness = new PalleteBusiness();
	}
	
	private TableView<PalleteModel> createTableView(){
		TableView<PalleteModel> table = new TableView<PalleteModel>();
		
        table.setEditable(false);
        table.setPrefWidth(1000);
        table.setMaxWidth(1000);
        table.setMaxHeight(6000);
        
        TableColumn<PalleteModel, String> nomeCol = new TableColumn<PalleteModel, String>("Produto");
        nomeCol.setMaxWidth(300);
        nomeCol.setCellValueFactory(new Callback<CellDataFeatures<PalleteModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<PalleteModel, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getProduto().getDescricao());
            }
         });
        
        TableColumn<PalleteModel, String> codigoBarras = new TableColumn<PalleteModel, String>("Código de Barras");
        codigoBarras.setMaxWidth(100);
        codigoBarras.setCellValueFactory(new Callback<CellDataFeatures<PalleteModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<PalleteModel, String> p) {
                return new ReadOnlyObjectWrapper((p.getValue().getProduto().getCodigoBarras()));
            }
         });
        
        TableColumn<PalleteModel, String> fscCode = new TableColumn<PalleteModel, String>("Fsc Code");
        fscCode.setMaxWidth(100);
        fscCode.setCellValueFactory(new Callback<CellDataFeatures<PalleteModel, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<PalleteModel, String> p) {
                return new ReadOnlyObjectWrapper((p.getValue().getProduto().getFscCode()));
            }
         });
        
        TableColumn<PalleteModel, Integer> codigoCol = new TableColumn<PalleteModel, Integer>("Quantidade");
        codigoCol.setMinWidth(100);
        codigoCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, Integer>("quantidadeProdutos"));

        TableColumn<PalleteModel, String> codigoBarrasCol = new TableColumn<PalleteModel, String>("Identificação");
        codigoBarrasCol.setMinWidth(100);
        codigoBarrasCol.setCellValueFactory(new PropertyValueFactory<PalleteModel, String>("identificacao"));
        
        List<PalleteModel> listaProdutos = null;
		try {
			listaProdutos = palleteBusiness.selectStatusAtualPalletes();
		} catch (Exception e) {
			ShowAlertUtil.exibirMensagemErro("Erro -> "+e.getMessage());
		}
        conteudoTabela = FXCollections.observableArrayList(listaProdutos);
        table.setItems(conteudoTabela);
        table.getColumns().addAll(codigoBarrasCol,nomeCol,codigoBarras,fscCode,codigoCol);
        
        return table;
	}
	
	
	public void buildMenuItem(Stage primaryStage){
		Scene scene = new Scene(new Group());
	    final Label label = new Label("Pallets");
        label.setFont(new Font("Arial", 20));

        TableView<PalleteModel> table = createTableView();
        
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
