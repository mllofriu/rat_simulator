import java.io.File;
import java.util.Hashtable;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.media.j3d.*;
import javax.vecmath.*;

public class WorldConstructor {
    Document doc;
    worldBranchGroup wb1 = new worldBranchGroup();
    worldBranchGroup wb2 = new worldBranchGroup();
    worldBranchGroup wb3 = new worldBranchGroup();
        
    public WorldConstructor(String File) {
        try {
	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

	    doc = docBuilder.parse(new File(File));

	    doc.getDocumentElement ().normalize ();

	} catch (SAXParseException err) {
	    System.out.println ("** Parsing error" 
		+ ", line " + err.getLineNumber ()
		+ ", uri " + err.getSystemId ());
	    System.out.println("   " + err.getMessage ());
	} catch (SAXException e) {
	    Exception	x = e.getException ();
	    ((x == null) ? e : x).printStackTrace ();
	} catch (Throwable t) {
	    t.printStackTrace ();
	}
        
        constructGroup();
    }
    
    public void constructGroup() {
        NodeList list;
        org.w3c.dom.Node node, nodeaux;
        NamedNodeMap attributes;
        int i;
        String blockName=null;
        list = doc.getElementsByTagName("sphere");
        for(i=0; i<list.getLength(); i++) {
            float r, cr, cg, cb, xp, yp, zp;
            
            node = list.item(i);
            attributes = node.getAttributes();
            nodeaux = attributes.getNamedItem("r");
            r = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //color
            nodeaux = attributes.getNamedItem("cr");
            cr = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cg");
            cg = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cb");
            cb = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //position
            nodeaux = attributes.getNamedItem("xp");
            xp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("yp");
            yp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("zp");
            zp = (Float.parseFloat(nodeaux.getNodeValue()));
            
            wb1.addSphere(r, new Color3f(cr, cg, cb), xp, yp, zp);
            wb2.addSphere(r, new Color3f(cr, cg, cb), xp, yp, zp);
            wb3.addSphere(r, new Color3f(cr, cg, cb), xp, yp, zp);
        }
        
        // busco tanques de morris
        
        list = doc.getElementsByTagName("pool");
        for(i=0; i<list.getLength(); i++) {
            float r, h, cr, cg, cb, xp, yp, zp;
            
            node = list.item(i);
            attributes = node.getAttributes();
            
            nodeaux = attributes.getNamedItem("r");
            r = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("h");
            h = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //color
            nodeaux = attributes.getNamedItem("cr");
            cr = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cg");
            cg = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cb");
            cb = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //position
            nodeaux = attributes.getNamedItem("xp");
            xp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("yp");
            yp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("zp");
            zp = (Float.parseFloat(nodeaux.getNodeValue()));
            
            wb1.addCylinderWall(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
            wb2.addCylinderWall(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
            wb3.addCylinderWall(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
        }
        
        //Cylinders
        list = doc.getElementsByTagName("cylinder");
        for(i=0; i<list.getLength(); i++) {
            float r, h, cr, cg, cb, xp, yp, zp;
            
            node = list.item(i);
            attributes = node.getAttributes();
            
            nodeaux = attributes.getNamedItem("r");
            r = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("h");
            h = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //color
            nodeaux = attributes.getNamedItem("cr");
            cr = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cg");
            cg = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cb");
            cb = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //position
            nodeaux = attributes.getNamedItem("xp");
            xp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("yp");
            yp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("zp");
            zp = (Float.parseFloat(nodeaux.getNodeValue()));
            
            wb1.addCylinder(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
            wb2.addCylinder(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
            wb3.addCylinder(r, h, new Color3f(cr, cg, cb), xp, yp, zp);
        }
        
        //Boxes
        list = doc.getElementsByTagName("box");
        for(i=0; i<list.getLength(); i++) {
        	// x,y,z: length, width, and height
            float x, y, z, cr, cg, cb, xp, yp, zp;
            
            node = list.item(i);
            attributes = node.getAttributes();
            nodeaux = attributes.getNamedItem("x");
            x = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("y");
            y = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("z");
            z = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //color
            nodeaux = attributes.getNamedItem("cr");
            cr = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cg");
            cg = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cb");
            cb = (Float.parseFloat(nodeaux.getNodeValue()));
            
            //position
            nodeaux = attributes.getNamedItem("xp");
            xp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("yp");
            yp = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("zp");
            zp = (Float.parseFloat(nodeaux.getNodeValue()));
            if (attributes.getNamedItem(Simulation.STR_NAME)!=null)
            	blockName=attributes.getNamedItem(Simulation.STR_NAME).getNodeValue();
            // TODO: ojo que estaba en true
            wb1.addBox(blockName,x, y, z, new Color3f(cr, cg, cb), xp, yp, zp);
            wb2.addBox(blockName,x, y, z, new Color3f(cr, cg, cb), xp, yp, zp);
            wb3.addBox(blockName,x, y, z, new Color3f(cr, cg, cb), xp, yp, zp);

        }
        
        
        //floor
        list = doc.getElementsByTagName("floor");
        node = list.item(0);
        attributes = node.getAttributes();
        
        nodeaux = attributes.getNamedItem("r");
        float r = (Float.parseFloat(nodeaux.getNodeValue()));
        //color
        nodeaux = attributes.getNamedItem("cr");
        float cr = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("cg");
        float cg = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("cb");
        float cb = (Float.parseFloat(nodeaux.getNodeValue()));          
        //position
        nodeaux = attributes.getNamedItem("xp");
        float xp = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("yp");
        float yp = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("zp");
        float zp = (Float.parseFloat(nodeaux.getNodeValue()));
        
        wb1.addCylinder(r, 0, new Color3f(cr, cg, cb), xp, yp, zp);
        wb2.addCylinder(r, 0, new Color3f(cr, cg, cb), xp, yp, zp);
        wb3.addCylinder(r, 0, new Color3f(cr, cg, cb), xp, yp, zp);
        
        
        //top view
        float x, y, z;
        list = doc.getElementsByTagName("topview");
        node = list.item(0);
        attributes = node.getAttributes();
        nodeaux = attributes.getNamedItem("x");
        x = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("y");
        y = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("z");
        z = (Float.parseFloat(nodeaux.getNodeValue()));
        wb1.setTopView(new Vector3f(x,y,z));
        wb2.setTopView(new Vector3f(x,y,z));
        wb3.setTopView(new Vector3f(x,y,z));
        
        //thirdview
        list = doc.getElementsByTagName("thirdview");
        node = list.item(0);
        attributes = node.getAttributes();
        nodeaux = attributes.getNamedItem("x");
        x = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("y");
        y = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("z");
        z = (Float.parseFloat(nodeaux.getNodeValue()));
        wb1.setThirdView(new Vector3f(x,y,z));
        wb2.setThirdView(new Vector3f(x,y,z));
        wb3.setThirdView(new Vector3f(x,y,z));
        
        //robot view
        list = doc.getElementsByTagName("robotview");
        node = list.item(0);
        attributes = node.getAttributes();
        nodeaux = attributes.getNamedItem("x");
        x = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("y");
        y = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("z");
        z = (Float.parseFloat(nodeaux.getNodeValue()));
        wb1.setRobotView(new Vector3f(x,y,z));
        wb2.setRobotView(new Vector3f(x,y,z));
        wb3.setRobotView(new Vector3f(x,y,z));

        //food
        float rF, hF, crF, cgF, cbF, xF, yF, zF;
        list = doc.getElementsByTagName(worldBranchGroup.STRING_FOOD);
        node = list.item(0);
        attributes = node.getAttributes();
        nodeaux = attributes.getNamedItem("r");
        rF = (Float.parseFloat(nodeaux.getNodeValue()));
        nodeaux = attributes.getNamedItem("h");
        hF = (Float.parseFloat(nodeaux.getNodeValue()));
        //color
        nodeaux = attributes.getNamedItem("cr");
            crF = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cg");
            cgF = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("cb");
            cbF = (Float.parseFloat(nodeaux.getNodeValue()));
            //position
            nodeaux = attributes.getNamedItem("xp");
            xF = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("yp");
            yF = (Float.parseFloat(nodeaux.getNodeValue()));
            nodeaux = attributes.getNamedItem("zp");
            zF = (Float.parseFloat(nodeaux.getNodeValue()));
            
        wb1.addFood(rF, hF, new Color3f(crF, cgF, cbF), xF, yF, zF);
        wb2.addFood(rF, hF, new Color3f(crF, cgF, cbF), xF, yF, zF);
        wb3.addFood(rF, hF, new Color3f(crF, cgF, cbF), xF, yF, zF);
        
        
        addDirectionalLight(new Vector3f(0f, 0f, -5), new Color3f(1f, 1f, 1f));
        addDirectionalLight(new Vector3f(0f, 0f, 5), new Color3f(.5f, .5f, .5f));
        addDirectionalLight(new Vector3f(0f, -5f, -5), new Color3f(.5f, .5f, .5f));
        addDirectionalLight(new Vector3f(0f, 5f, -5), new Color3f(.5f, .5f, .5f));
        addDirectionalLight(new Vector3f(0f, -5f, 5), new Color3f(.5f, .5f, .5f));
        addDirectionalLight(new Vector3f(0f, 5f, 5), new Color3f(.5f, .5f, .5f));
        addDirectionalLight(new Vector3f(0f, -5, 0), new Color3f(1f, 1f, 1f));
    }
    
    public void remove(String nameVolume) {
    	wb1.remove(nameVolume);
    	wb2.remove(nameVolume);
    	wb3.remove(nameVolume);
    }
    
	public void move(String nameVolume, Point4d point) {
    	wb1.move(nameVolume, point);
    	wb2.move(nameVolume, point);
    	wb3.move(nameVolume, point);
    }

    public void add(String nameVolume) {
    	wb1.add(nameVolume);
    	wb2.add(nameVolume);
    	wb3.add(nameVolume);
    }
    
    public worldBranchGroup getFrontGroup() {
        return wb1;
    }
    
    public worldBranchGroup getTopGroup() {
        return wb2;
    }
    
    public worldBranchGroup getLastGroup() {
        return wb3;
    }
    
    public void addDirectionalLight(Vector3f direction, Color3f color) {
	BoundingSphere bounds = new BoundingSphere();
	bounds.setRadius(1000d);
        BoundingSphere bounds1 = new BoundingSphere();
	bounds1.setRadius(1000d);
        BoundingSphere bounds2 = new BoundingSphere();
	bounds2.setRadius(1000d);

	DirectionalLight lightD = new DirectionalLight(color, direction);
        lightD.setInfluencingBounds(bounds);
        DirectionalLight lightD1 = new DirectionalLight(color, direction);
        lightD1.setInfluencingBounds(bounds1);
        DirectionalLight lightD2 = new DirectionalLight(color, direction);
        lightD2.setInfluencingBounds(bounds2);

        wb1.addChild(lightD);
        wb2.addChild(lightD1);
        wb3.addChild(lightD2);
    }

}
