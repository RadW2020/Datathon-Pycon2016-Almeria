/*
 * Programa creado para la participación en el Datathon de Cajamar Data Lab
 * 
 * PyconES 2016 - Almería
 */
package brain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


/**
 * Clase que hace uso de la librearía weka machine learning 
 * para predecir el comportamiento de clientes
 * Se tiene un set de entrenamiento completo y un set sin uno de los campos
 * Este proyecto hace uso de la Técnica Random Forest esperando que la
 * mezcla de valores discretos y reales favorezca un buen resultado
 * Este programa hace uso de otro para preparar los archivos, 
 * haciendo uso de una base de datos, que ha sido codificado para ese propósito.
 *
 * @author RadW2020 
 */
public class General {
    
    
    /**
     * Al final no uso este método ya que importo de una base de datos pgSQL
     * con otro programa
     * @param archivoDataset
     * @throws IOException 
     */
    public void CajaMarDataSet2CSV(File archivoDataset) throws IOException{
        
        Path path = Paths.get(archivoDataset.getName());
        Charset charset = StandardCharsets.UTF_8;
        
        String content = new String(Files.readAllBytes(path), charset);
        if ((content.contains("ID|"))){
            content = content.replaceAll("," , ".");
            content = content.replaceAll("\\|" , ",");
            Files.write(path, content.getBytes(charset));
            System.out.println("Archivo "
                    +"'" + archivoDataset.toString() + "'"
                    + " modificado a tipo CSV");   
        } else {
            System.out.println("Archivo no es del tipo correcto "
                    + "o ya ha sido modificado");
        }
    }
    
    /**
     * Al final no uso este método porque filtro el dataset en buffer
     * @param archivDataset
     * @param archivSalida
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void quitarID(File archivDataset, File archivSalida) throws FileNotFoundException, IOException{
        Path path = Paths.get(archivDataset.getName());
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path), charset);
        
        FileReader fr = new FileReader(archivDataset);
        BufferedReader br = new BufferedReader(fr);
        PrintWriter pw = new PrintWriter(archivSalida);
        BufferedWriter bw = new BufferedWriter(pw);
        
        String linea;
        if ( !(content.contains("ID")) ) {
            System.out.println("El archivo no tiene ID");
        } else {
            
            bw.write(br.readLine().replaceFirst("ID," , ""));
            bw.newLine(); 
            while ((linea = br.readLine()) != null){
                
                bw.write(linea.replaceFirst("[0-9]+,*" , ""));
                bw.newLine(); 
            }
        }
        bw.close();
        br.close();
        pw.close();
        fr.close();
    }
    
    /**
     * Método para configurar el encabezado de los archivos csv conformados
     * desde la base de datos y que sean compatibles con la librería weka
     * @param archivDataset
     * @param archivSalida
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void encabezadoARFF(File archivDataset, File archivSalida) throws FileNotFoundException, IOException{
        
        FileReader fr = new FileReader(archivDataset);
        BufferedReader br = new BufferedReader(fr);
        PrintWriter pw = new PrintWriter(archivSalida);
        BufferedWriter bw = new BufferedWriter(pw);
        
        StringBuilder encabezado = new StringBuilder();
        encabezado.append("@relation " 
                + (archivDataset.getName().substring(0, archivDataset.getName().indexOf("."))) + "\n" +
            "\n" );
        if (archivDataset.getName().contains("Test")){
            encabezado.append("@attribute ID real\n");
        }
        encabezado.append("@attribute socio_demo_01 {0, 1, 2}\n" +
            "@attribute ind_prod_01 {0, 1, 2}\n" +
            "@attribute ind_prod_02 {0, 1, 2}\n" +
            "@attribute ind_prod_03 {0, 1, 2}\n" +
            "@attribute ind_tend_09 {0, 1, 2, 3, 4, 5}\n" +
            "@attribute ind_prod_04 {0, 1, 2}\n" +
            "@attribute ind_tend_02 {0, 1, 2, 3, 4}\n" +
            "@attribute ind_prod_05 {0, 1, 2}\n" +
            "@attribute ind_prod_06 {0, 1, 2}\n" +
            "@attribute ind_tend_08 {0, 1, 2, 3}\n" +
            "@attribute ind_prod_07 {0, 1, 2}\n" +
            "@attribute ind_tend_07 {0, 1, 2, 3, 4}\n" +
            "@attribute ind_prod_08 {0, 1, 2}\n" +
            "@attribute ind_tend_01 {0, 1, 2, 3, 4}\n" +
            "@attribute ind_tend_04 {0, 1, 2, 3, 4}\n" +
            "@attribute ind_tend_06 {0, 1, 2, 3, 4}\n" +
            "@attribute ind_tend_03 {0, 1, 2, 3, 4}\n" +
            "@attribute ind_tend_05 {0, 1, 2, 3, 4}\n" +
            "@attribute socio_demo_02 {0,1,2,3,4,5,6,7,8,9,10,11,12,13}\n" +
            "@attribute ind_prod_09 {0,1}\n" +
            "@attribute ind_prod_10 {0,1}\n" +
            "@attribute ind_prod_11 {0,1,2}\n" +
            "@attribute ind_prod_12 {0,1,2}\n" +
            "@attribute ind_prod_13 {0,1,2}\n" +
            "@attribute ind_prod_14 {0,1,2}\n" +
            "@attribute ind_prod_15 {0,1,2}\n" +
            "@attribute ind_prod_16 {0,1,2}\n" +
            "@attribute ind_prod_17 {0,1}\n" +
            "@attribute ind_prod_18 {0,1,2}\n" +
            "@attribute ind_prod_19 {0,1,2}\n" +
            "@attribute ind_prod_20 {0,1,2}\n" +
            "@attribute ind_prod_21 {0,1,2}\n" +
            "@attribute ind_prod_22 {0,1,2}\n" +
            "@attribute ind_prod_23 {0,1,2}\n" +
            "@attribute imp_sal_01 real\n" +
            "@attribute imp_sal_02 real\n" +
            "@attribute imp_sal_03 real\n" +
            "@attribute num_oper_05 real\n" +
            "@attribute imp_sal_16 real\n" +
            "@attribute num_oper_06 real\n" +
            "@attribute num_oper_07 real\n" +
            "@attribute imp_sal_17 real\n" +
            "@attribute num_oper_08 real\n" +
            "@attribute imp_sal_18 real\n" +
            "@attribute num_oper_09 real\n" +
            "@attribute imp_sal_19 real\n" +
            "@attribute num_oper_10 real\n" +
            "@attribute imp_sal_04 real\n" +
            "@attribute num_oper_11 real\n" +
            "@attribute imp_sal_15 real\n" +
            "@attribute num_oper_12 real\n" +
            "@attribute imp_sal_20 real\n" +
            "@attribute imp_sal_05 real\n" +
            "@attribute imp_sal_06 real\n" +
            "@attribute imp_sal_07 real\n" +
            "@attribute imp_sal_08 real\n" +
            "@attribute num_oper_13 real\n" +
            "@attribute imp_sal_09 real\n" +
            "@attribute imp_sal_10 real\n" +
            "@attribute num_oper_14 real\n" +
            "@attribute num_oper_04 real\n" +
            "@attribute num_oper_03 real\n" +
            "@attribute num_oper_15 real\n" +
            "@attribute num_oper_16 real\n" +
            "@attribute num_oper_17 real\n" +
            "@attribute imp_sal_11 real\n" +
            "@attribute imp_sal_12 real\n" +
            "@attribute num_oper_02 real\n" +
            "@attribute num_oper_18 real\n" +
            "@attribute num_oper_19 real\n" +
            "@attribute num_oper_20 real\n" +
            "@attribute num_oper_21 real\n" +
            "@attribute imp_sal_13 real\n" +
            "@attribute imp_sal_14 real\n" +
            "@attribute num_oper_22 real\n" +
            "@attribute num_oper_23 real\n" +
            "@attribute num_oper_24 real\n" +
            "@attribute imp_sal_21 real\n" +
            "@attribute socio_demo_03 real\n" +
            "@attribute socio_demo_04 real\n" +
            "@attribute imp_cons_02 real\n" +
            "@attribute imp_cons_03 real\n" +
            "@attribute imp_cons_04 real\n" +
            "@attribute imp_cons_05 real\n" +
            "@attribute imp_cons_06 real\n" +
            "@attribute imp_cons_07 real\n" +
            "@attribute imp_cons_08 real\n" +
            "@attribute imp_cons_09 real\n" +
            "@attribute imp_cons_10 real\n" +
            "@attribute imp_cons_12 real\n" +
            "@attribute imp_cons_13 real\n" +
            "@attribute imp_cons_14 real\n" +
            "@attribute imp_cons_15 real\n" +
            "@attribute imp_cons_16 real\n" +
            "@attribute imp_cons_17 real\n" +
            "@attribute imp_cons_01 real\n" +
            "@attribute socio_demo_05 real\n");
        if (archivDataset.getName().contains("Train")){
            encabezado.append("@attribute target {0,1}\n");
        }
        if (archivDataset.getName().contains("Test")){
            encabezado.append("@attribute resultado {0,1}\n");
        }
            encabezado.append(
            "\n" +
            "@data");
        pw.write(encabezado.toString());
        String linea;
        br.readLine();
        while ((linea = br.readLine()) != null){
            bw.write("\n");
            bw.write(linea);
            if (archivDataset.getName().contains("Test")){
            bw.write(",?");//para el nuevo campo de testDS que será el resultado
            }
        }      
        br.close();
        bw.close();
        pw.close();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
                
        General woo = new General();
                
        File trainSinID = new File("TrainTableSinID.csv");
        File testConID = new File("TestTableConId.csv");
                  
        //Poner encabezado tipo ARFF para trabajar con la librería WEKA
        File trainSinID2ARFF = new File("TrainTableSinID.arff");
        File testConID2ARFF = new File("TestTableConId.arff");
        woo.encabezadoARFF(trainSinID, trainSinID2ARFF);
        woo.encabezadoARFF(testConID, testConID2ARFF);
                
        //DATASOURCE sobre los archivos ARFF
        DataSource source = new DataSource( "TrainTableSinID.arff" );
        DataSource source2 = new DataSource( "TestTableConId.arff" );
        // INSTANCIAS DATASET de trainTable y testTable
        Instances trainDS = source.getDataSet();
        Instances testDS = source2.getDataSet();
        
        
        // CLASS INDEX declarado para cada uno de los sets
        trainDS.setClassIndex(trainDS.numAttributes()-1);
        testDS.setClassIndex(testDS.numAttributes()-1);
        
        /*
        Se graba el modelo en un archivo si este no existe.
        Se aumenta la rapidez de las pruebas una vez el modelo es válido.
        Si se quiere otro modelo solo hay que borrar el archivo model.
        */
        RandomForest arboleda;
        if ( !(new File("RFmodel.model")).exists() ){
            arboleda = new RandomForest(); //clasificador tipo randomForest
            arboleda.buildClassifier(trainDS); //entrenamos el modelo con el set
            System.out.println("Nuevo modelo creado " + arboleda); // ouput check
            // save model
            weka.core.SerializationHelper.write("RFmodel.model", arboleda);
        } else {
            arboleda = (RandomForest) weka.core.SerializationHelper.read("RFmodel.model"); //Lee del archivo creado anteriormente
            System.out.println("Modelo serializado"); // output check
        }
        /* COMPROBACIONES VARIAS POR CONSOLA */
        System.out.println("scheme.getCapabilities() " + arboleda.getCapabilities().toString());
        System.out.println("scheme " + arboleda);
        System.out.println("scheme.getTechnicalInformation() " + arboleda.getTechnicalInformation());
        System.out.println("numt atrib trainDS " + trainDS.numAttributes());
        System.out.println("numt atrib testDS " + testDS.numAttributes());
        System.out.println("num instancias trainDS " + trainDS.numInstances());
        System.out.println("num instancias de testDS: " + testDS.numInstances());
        System.out.println("trainDS class index: " + trainDS.attribute("target").name());
        System.out.println("testDS class index: " + testDS.attribute(testDS.numAttributes()-1).name());
        
        //Se evalua el modelo 
        Evaluation eval = new Evaluation(trainDS); 
        eval.evaluateModel(arboleda, trainDS); 
                
        /*************  OPCION CROSS VALIDATION  **********************/
        /*
        eval.crossValidateModel(arboleda, trainDS, 3, new Random(1)); //Descomentar linea para hacer pruebas CV
        System.out.println(eval.toSummaryString("CV Evaluation results: ", false)); //Descomentar linea para hacer pruebas CV
        */
        /*  
            Cross Validation sobre el mismo set de entrenamiento 
            Este test en mi equipo ha necesitado 11 minutos y 12Gb de Java Heap
            
            Resultado con 2 folds: 
                Correlation coefficient                  0.795 
                Mean absolute error                    135.7689
                Root mean squared error                431.1352
                Relative absolute error                 31.0763 %
                Root relative squared error             61.5003 %
                Total Number of Instances           471838
            Resultado con 3 folds: (23 minutos)
                Correlation coefficient                  0.7983
                Mean absolute error                    131.3413
                Root mean squared error                427.3762
                Relative absolute error                 30.0629 %
                Root relative squared error             60.9641 %
                Total Number of Instances           471838 
        */
        /*********************************************************/
        
        /******************* STATS OUTPUT **********************/
        System.out.println("==========  EVALUATION INFO TRAIN SET =========");
        System.out.println("Complex Stats \n" + eval.toSummaryString(true));
        System.out.println("Correct % = " + eval.pctCorrect());
        System.out.println("Incorrect % = " + eval.pctIncorrect());
        System.out.println("MAE = " + eval.meanAbsoluteError());
        System.out.println("RMSE = " + eval.rootMeanSquaredError());
        System.out.println("RAE = " + eval.relativeAbsoluteError());
        System.out.println("RRSE = " + eval.rootRelativeSquaredError());
        
                
        // quitamos el atributo ID
        testDS.deleteAttributeAt(0);
        //output check
        System.out.println("numt atrib testDS " + testDS.numAttributes());
        System.out.println("numt instan testDS " + testDS.numInstances());
        
        
        int count = 0;
        int ceros = 0;
        int unos = 0;
        /************************ PREDICCION ****************************/
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < testDS.numInstances(); i++){
            // Toma el valor de la instancia actual
            double actualClass = testDS.instance(i).classValue();
            // Toma el índice a modo String
            String actual = testDS.classAttribute().value((int) actualClass);
            // Crea una nueva instancia
            Instance newInst = testDS.instance(i);
            // Clasifica la nueva instancia sin ID y guarda el valor del resultado
            double predScheme = arboleda.classifyInstance(newInst);
            // Prepara el valor del resultado en String
            String predString = testDS.classAttribute().value((int) predScheme);
            //System.out.print("AUC = " + testDS.areaUnderROC(i) + " ");
            //System.out.println(actual + " -> " + predString);
            if (predString.contentEquals("1")){
                unos++;
            }
            if (predString.contentEquals("0")){
                ceros++;
            }
            res.append(predString+"\n");
            count++;
        }
        System.out.println("count " + count);
        System.out.println("unos " + unos);
        System.out.println("ceros " + ceros);
        /*************************************************************/
        
        /*** Preparar archivo set para datathon ****/
        
        File testResultado = new File("test") ;
        
        PrintWriter pw = new PrintWriter(testResultado);
        BufferedWriter bw = new BufferedWriter(pw);
        StringBuilder op = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("soloID.csv"));
        
        
        pw.write("RESULTADO\n");
        pw.write(res.toString());
        pw.close();
        
        //Evaluation evalTest = new Evaluation(testDS);
        eval.evaluateModel(arboleda, testDS);
        System.out.println("FIN : " + eval.toSummaryString());
        for (int i=0; i<(eval.predictions().size()-1); i++){
                
                op.append(eval.predictions().get(i).predicted() + "\n");
        }
        
                
        
    }
}


        