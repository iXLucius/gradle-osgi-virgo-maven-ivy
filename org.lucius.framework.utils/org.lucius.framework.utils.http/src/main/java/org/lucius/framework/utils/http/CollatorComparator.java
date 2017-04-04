package org.lucius.framework.utils.http;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

public class CollatorComparator implements Comparator<String> {
	Collator collator = Collator.getInstance();
	public int compare(String o1, String o2) {
		CollationKey key1 = collator.getCollationKey(o1);
		CollationKey key2 = collator.getCollationKey(o2);
		return key1.compareTo(key2);
	}

}
