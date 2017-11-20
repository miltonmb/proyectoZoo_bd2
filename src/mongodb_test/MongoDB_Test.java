package mongodb_test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.rmi.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import org.bson.Document;

public class MongoDB_Test {

    public static void main(String[] args) throws UnknownHostException {
        Scanner sc = new Scanner(System.in);
        MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds044709.mlab.com:44709/proyectoz");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        int opcion = 0;
        char r = 's';
        while (r == 's' || r == 'S') {
            System.out.println("Ingrese una opcion:"
                    + "\n1.Agregar empleado"
                    + "\n2.Listar un empleado"
                    + "\n3.Modificar un empleado"
                    + "\n4.Borrar un empleado "
                    + "\n5. Listar todos");
            opcion = sc.nextInt();
            String nombre = "";
            String cargo = "";
            int id = 0;
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la identificacion del empleado: ");
                    id = sc.nextInt();
                    System.out.println("Ingrese el nombre del empleado: ");
                    nombre = sc.next();
                    System.out.println("Ingrese el cargo del empleado: ");
                    cargo = sc.next();
                    Document doc = new Document();
                    doc.append("_id", id);
                    doc.append("Nombre", nombre);
                    doc.append("Cargo", cargo);
                    db.getCollection("Empleados").insertOne(doc);

                    break;
                case 2:
                    int idLectura;
                    System.out.println("Ingrese la identificacion del empleado a listar: ");
                    idLectura = sc.nextInt();
                    BasicDBObject query = new BasicDBObject();
                    query.put("_id", idLectura);
                    System.out.println(db.getCollection("Empleados").find(query).first());

                    break;
                case 3:
                    int idModificar;
                    System.out.println("Ingrese la identificacion del empleado a modificar: ");
                    idModificar = sc.nextInt();
                    System.out.println("Ingrese el nombre del empleado: ");
                    nombre = sc.next();
                    System.out.println("Ingrese el cargo del empleado: ");
                    cargo = sc.next();
                    db.getCollection("Empleados").updateOne(Filters.eq("_id", idModificar), Updates.set("Nombre", nombre));
                    db.getCollection("Empleados").updateOne(Filters.eq("_id", idModificar), Updates.set("Cargo", cargo));
                    break;
                case 4:
                    int idEliminar;
                    System.out.println("Ingrese la identificacion del empleado a eliminar: ");
                    idEliminar = sc.nextInt();
                    db.getCollection("Empleados").deleteOne(Filters.eq("_id", idEliminar));
                    break;

                case 5:
                    FindIterable<Document> iterDoc = db.getCollection("Empleados").find();
                    int i = 1;
                    Iterator it = iterDoc.iterator();
                    while (it.hasNext()) {
                        System.out.println(it.next());
                        i++;
                    }
                    break;
            }
            System.out.println("Desea continuar(s/n): ");
            r = sc.next().charAt(0);
        }
    }
}
