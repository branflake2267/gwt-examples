package com.tribling.gwt.test.oauth.client;

import java.util.ArrayList;

/**
 * testing this out only!!!!
 * 
 * I got no clue yet to what I am doing, so I am moving on to use gwt md5 generator for now
 * and will come back to this later
 * 
 * @author branflake2267
 * 
 */
public class Sha1 {
 
	/*
	 * A JavaScript implementation of the Secure Hash Algorithm, SHA-1, as
	 * defined in FIPS PUB 180-1 Version 2.1a Copyright Paul Johnston 2000 -
	 * 2002. Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
	 * Distributed under the BSD License See http://pajhome.org.uk/crypt/md5 for
	 * details.
	 */

	/*
	 * Configurable variables. You may need to tweak these to be compatible with
	 * the server-side, but the defaults work in most cases.
	 */
	
	/*
	 * hex output format. 0 - lowercase; 1 -
	 * uppercase
	 */
	private boolean hexcase = false; 
	
	/*
	 * base-64 pad character. "=" for strict RFC
	 * compliance
	 */
	private String b64pad = "";
	
	/* bits per input character. 8 - ASCII; 16 - Unicode */
	private int chrsz = 8; 

	/**
	 * constructor
	 */
	public Sha1() {
	}
	
	public String hex_sha1(String s) {
		return binb2hex(core_sha1(str2binb(s), s.length() * chrsz));
	}

	public String b64_sha1(String s) {
		return binb2b64(core_sha1(str2binb(s), s.length() * chrsz));
	}

	public String str_sha1(String s) {
		return binb2str(core_sha1(str2binb(s), s.length() * chrsz));
	}

	public String hex_hmac_sha1(String key, String data) {
		return binb2hex(core_hmac_sha1(key, data));
	}

	public String b64_hmac_sha1(String key, String data) {
		return binb2b64(core_hmac_sha1(key, data));
	}

	public String str_hmac_sha1(String key, String data) {
		return binb2str(core_hmac_sha1(key, data));
	}

	/*
	 * Perform a simple self-test to see if the VM is working
	 */
	public boolean sha1_vm_test() {

		return hex_sha1("abc") == "a9993e364706816aba3e25717850c26c9cd0d89d";
	}

	/*
	 * Calculate the SHA-1 of an array of big-endian words, and a bit length
	 */
	private int[] core_sha1(int[] x, int len) {
		
		/* append padding */
		x[len >> 5] |= 0x80 << (24 - len % 32);
		x[((len + 64 >> 9) << 4) + 15] = len;

		int[] w = new int[80];
		int a = 1732584193;
		int b = -271733879;
		int c = -1732584194;
		int d = 271733878;
		int e = -1009589776;

		for (int i = 0; i < x.length; i += 16) {
			int olda = a;
			int oldb = b;
			int oldc = c;
			int oldd = d;
			int olde = e;

			for (int j = 0; j < 80; j++) {
				if (j < 16)
					w[j] = x[i + j];
				else
					w[j] = rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
				int t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)),
						safe_add(safe_add(e, w[j]), sha1_kt(j)));
				e = d;
				d = c;
				c = rol(b, 30);
				b = a;
				a = t;
			}

			a = safe_add(a, olda);
			b = safe_add(b, oldb);
			c = safe_add(c, oldc);
			d = safe_add(d, oldd);
			e = safe_add(e, olde);
		}

		return new int[] { a, b, c, d, e };
	}

	/*
	 * Perform the appropriate triplet combination private for the current
	 * iteration
	 */
	private int sha1_ft(int t, int b, int c, int d) {
		if (t < 20)
			return (b & c) | ((~b) & d);
		if (t < 40)
			return b ^ c ^ d;
		if (t < 60)
			return (b & c) | (b & d) | (c & d);
		return b ^ c ^ d;
	}

	/*
	 * Determine the appropriate additive constant for the current iteration
	 */
	private int sha1_kt(int t) {
		return (t < 20) ? 1518500249 : (t < 40) ? 1859775393
				: (t < 60) ? -1894007588 : -899497514;
	}

	/**
	 * Calculate the HMAC-SHA1 of a key and some data
	 * 
	 * Modified: had to add a array joining method
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	private int[] core_hmac_sha1(String key, String data) {
		int[] bkey = str2binb(key);
		if (bkey.length > 16) {
			bkey = core_sha1(bkey, key.length() * chrsz);
		}
			
		int[] ipad = new int[16];
		int[] opad = new int[16];
		for (int i = 0; i < 16; i++) {
			ipad[i] = bkey[i] ^ 0x36363636;
			opad[i] = bkey[i] ^ 0x5C5C5C5C;
		}

		// modified with join method
		int[] hash = core_sha1(join(ipad,str2binb(data)), 512 + data.length() * chrsz);

		// modified with join method
		return core_sha1(join(opad,hash), 512 + 160);
	}

	/**
	 * joint to int[] arrays into one int[] array
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private int[] join(int[] a, int[] b) {
		int[] c = new int[a.length + b.length];
		int index = 0;
		for(int i=0; i < a.length; i++) {
			c[index] = a[i];
			index++;
		}
		for (int i=0; i< b.length; i++) {
			c[index] = b[i];
			index++;
		}
		return c;
	}
	
	/*
	 * Add integers, wrapping at 2^32. This uses 16-bit operations internally to
	 * work around bugs in some JS interpreters.
	 */
	private int safe_add(int x, int y) {
		int lsw = (x & 0xFFFF) + (y & 0xFFFF);
		int msw = (x >> 16) + (y >> 16) + (lsw >> 16);
		return (msw << 16) | (lsw & 0xFFFF);
	}

	/*
	 * Bitwise rotate a 32-bit number to the left.
	 */
	private int rol(int num, int cnt) {
		return (num << cnt) | (num >>> (32 - cnt));
	}

	/**
	 * Convert an 8-bit or 16-bit string to an array of big-endian words In
	 * 8-bit private, characters >255 have their hi-byte silently ignored.
	 * 
	 * I had to modify the unicode and adding to an array. This was a big rewrite for this method
	 * 
	 * @param str
	 * @return
	 */
	private int[] str2binb(String str) {
		int mask = (1 << chrsz) - 1;
		int index = 0;
		int bin = 0;
		ArrayList<Integer> ar = new ArrayList<Integer>();
		for (int i = 0; i < str.length() * chrsz; i += chrsz) {
			
			int pos = (i / chrsz) & mask;
			byte unicode = (byte) str.substring(pos, pos+1).charAt(0);
			int cal = unicode << (32 - chrsz - i % 32);

			if (i>>5 > index) {
				index++;
				ar.add(bin);
				bin = 0;
			}
			bin |=  cal;
			
			// debug
			//System.out.println("i:" + i + " mask: " + mask + " pos:" + pos + " charUnicode:" + unicode + " cal:" + cal + " bin:" + bin);
		}
		ar.add(bin);
		
		int[] rtn = new int[ar.size()];
		for (int i=0; i < ar.size(); i++) {
			rtn[i] = ar.get(i);
		}
		
		return rtn;
	}

	/**
	 * Convert an array of big-endian words to a string
	 * 
	 * @param bin
	 * @return
	 */
	private String binb2str(int[] bin) {
		String str = "";
		int mask = (1 << chrsz) - 1;
		for (int i = 0; i < bin.length * 32; i += chrsz) {
			str += (char) ((bin[i >> 5] >>> (32 - chrsz - i % 32)) & mask);
		}
		return str;
	}

	/*
	 * Convert an array of big-endian words to a hex string.
	 */
	private String binb2hex(int[] binarray) {
		String hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
		String str = "";
		for (int i = 0; i < binarray.length * 4; i++) {
			str += hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xF)
					+ hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xF);
		}
		return str;
	}

	/*
	 * Convert an array of big-endian words to a base-64 string
	 */
	private String binb2b64(int[] binarray) {
		String tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		String str = "";
		for (int i = 0; i < binarray.length * 4; i += 3) {
			int triplet = (((binarray[i >> 2] >> 8 * (3 - i % 4)) & 0xFF) << 16)
					| (((binarray[i + 1 >> 2] >> 8 * (3 - (i + 1) % 4)) & 0xFF) << 8)
					| ((binarray[i + 2 >> 2] >> 8 * (3 - (i + 2) % 4)) & 0xFF);
			for (int j = 0; j < 4; j++) {
				if (i * 8 + j * 6 > binarray.length * 32)
					str += b64pad;
				else
					str += tab.charAt((triplet >> 6 * (3 - j)) & 0x3F);
			}
		}
		return str;
	}

}
