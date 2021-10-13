package demo.arquillian.monolith.commons.domain;

import java.util.UUID;

public class IdGen {

	public static void main(String[] args) {
		for(int x = 1; x < 200; x++) {
			System.out.println(UUID.randomUUID().toString());
		}

	}

}
