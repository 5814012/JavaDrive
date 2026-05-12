package logica;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class GestorInformes {

    public void generarInformeXML(List<Cliente> clientes, List<Vehiculo> vehiculos) {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            ProcessingInstruction pi = doc.createProcessingInstruction(
                    "xml-stylesheet",
                    "type=\"text/xsl\" href=\"estilo_javadrive.xsl\""
            );
            doc.appendChild(pi);

            Element root = doc.createElement("javadrive");
            doc.appendChild(root);

            Element empresa = doc.createElement("empresa");
            root.appendChild(empresa);

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent("JavaDrive S.L.");
            empresa.appendChild(nombre);

            Element ubicacion = doc.createElement("ubicacion");
            ubicacion.setTextContent("Sede Central del PSOE");
            empresa.appendChild(ubicacion);

            Element flota = doc.createElement("flota");
            root.appendChild(flota);

            for (Vehiculo v : vehiculos) {
                Element vehiculo = doc.createElement("vehiculo");
                vehiculo.setAttribute("tipo", (v instanceof Coche) ? "Coche" : "Furgoneta");

                Element matricula = doc.createElement("matricula");
                matricula.setTextContent(v.getMatricula());
                vehiculo.appendChild(matricula);

                Element marca = doc.createElement("marca");
                marca.setTextContent(v.getMarca());
                vehiculo.appendChild(marca);

                Element modelo = doc.createElement("modelo");
                modelo.setTextContent(v.getModelo());
                vehiculo.appendChild(modelo);

                Element disponible = doc.createElement("disponible");
                disponible.setTextContent(String.valueOf(v.isDisponible()));
                vehiculo.appendChild(disponible);

                Element especifico = doc.createElement("especifico");
                especifico.setTextContent(v.obtenerDetalles());
                vehiculo.appendChild(especifico);

                flota.appendChild(vehiculo);
            }

            Element clientesNode = doc.createElement("clientes");
            root.appendChild(clientesNode);

            for (Cliente c : clientes) {
                Element cliente = doc.createElement("cliente");

                Element dni = doc.createElement("dni");
                dni.setTextContent(c.getDni());
                cliente.appendChild(dni);

                Element nombreC = doc.createElement("nombre");
                nombreC.setTextContent(c.getNombre());
                cliente.appendChild(nombreC);

                Element telefono = doc.createElement("telefono");
                telefono.setTextContent(c.getTelefono());
                cliente.appendChild(telefono);

                clientesNode.appendChild(cliente);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data/reporte_completo.xml"));

            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("[ERROR] Generando XML: " + e.getMessage());
        }
    }
}
