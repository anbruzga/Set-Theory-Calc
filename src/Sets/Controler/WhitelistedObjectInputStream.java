package Sets.Controler;

import Sets.View.PopUp;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WhitelistedObjectInputStream extends ObjectInputStream {
	private Set whitelist;

	public WhitelistedObjectInputStream(InputStream inputStream, Set wl) throws IOException {
		super(inputStream);
		whitelist = wl;
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass cls) throws IOException, ClassNotFoundException {
		if (!whitelist.contains(cls.getName())) {
			PopUp.infoBox("Please delete LogicAppData/tabledata.ser file and try again",
					"Security Error");
			throw new InvalidClassException("Unexpected serialized class", cls.getName());
		}
		return super.resolveClass(cls);
	}
}
/*
public class DeserializeExample {
	private static Object deserialize(byte[] buffer) throws IOException, ClassNotFoundException {
		Object ret = null;
		Set whitelist = new HashSet<String>(Arrays.asList(new String[]{"TableData","SingleTableLine"}));
		try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer)) {
			try (WhitelistedObjectInputStream ois = new WhitelistedObjectInputStream(bais, whitelist)) {
				ret = ois.readObject();
			}
		}
		return ret;
	}
}*/