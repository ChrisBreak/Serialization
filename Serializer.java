//Cristhian Sotelo-Plaza
//CPSC501 FALL 2019
//A3

import org.jdom2.*;
import java.lang.reflect.*;
import java.util.ArrayList;

public class Serializer {
	private ArrayList<Integer> refs = new ArrayList<Integer>();
	private ArrayList<Element> objs = new ArrayList<Element>();
	private Element root = new Element("serialized");

	public Document getDocument() {
		for (int i = objs.size()-1; i > -1; i--) {
			root.addContent(objs.get(i));
		}
		Document doc = new Document(root);

		return doc;
	}

	public void serialize(Object obj) {
		if (obj.getClass().isArray()) {
			serializeArray(obj);
		}
		else {
			serializeClass(obj);
		}
	}

	private void serializeClass(Object obj) {
		Element root = new Element("object");
		Class objClass = obj.getClass();
		int id = obj.hashCode();
		if (refs.contains(id)) {
			return;
		}
		refs.add(new Integer(id));
		root.setAttribute("class", objClass.getName());
		root.setAttribute("id", String.valueOf(id));

		if(objClass.getDeclaredFields().length>0){
			root = serializeFields(obj, root);
		}

		objs.add(root);
		//this.root.addContent(root);
	}

	private void serializeArray(Object obj) {
		Element root = new Element("object");
		Class objClass = obj.getClass();
		int id = obj.hashCode();
		if (refs.contains(id)) {
			return;
		}
		refs.add(new Integer(id));
		root.setAttribute("class", objClass.getName());
		root.setAttribute("id", String.valueOf(id));
		root.setAttribute("length", String.valueOf(Array.getLength(obj)));

		if (obj.getClass().getComponentType().isPrimitive()) {
			for (int i = 0; i < Array.getLength(obj); i++) {
        if (Array.get(obj, i) != null) {
					Element val = new Element("value");
					val.setText(String.valueOf(Array.get(obj, i)));
					root.addContent(val);
        }
      }
		}
		else {
			for (int i = 0; i < Array.getLength(obj); i++) {
        if (Array.get(obj, i) != null) {
					Object anOjb = Array.get(obj, i);
					int oId = anOjb.hashCode();
					Element ref = new Element("reference");
					ref.setText(String.valueOf(oId));
					root.addContent(ref);
					if (!refs.contains(new Integer(oId))) {
						serialize(anOjb);
					}
        }
      }
		}
		objs.add(root);
	}

	private Element serializeFields(Object obj, Element root) {
		Class objClass = obj.getClass();
		Field[] fields = objClass.getDeclaredFields();
		for(Field f : fields) {
			f.setAccessible(true);
			Element el = new Element("field");
			el.setAttribute("name", f.getName());
			el.setAttribute("declaringclass", objClass.getSimpleName());

			try {
				if (f.get(obj) != null) {
					if (!(f.getType().isPrimitive())) {
						Object fOjb = f.get(obj);
						int fId = fOjb.hashCode();
						Element ref = new Element("reference");
						ref.setText(String.valueOf(fId));
						el.addContent(ref);
						if (!refs.contains(new Integer(fId))) {
							serialize(fOjb);
						}
					}
					else {
						Element val = new Element("value");
						val.setText(String.valueOf(f.get(obj)));
						el.addContent(val);
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			root.addContent(el);
		}
		return root;
	}

}
