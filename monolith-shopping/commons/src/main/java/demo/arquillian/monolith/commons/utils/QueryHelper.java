package demo.arquillian.monolith.commons.utils;

import java.util.List;
import java.util.stream.Collectors;

import demo.arquillian.monolith.commons.domain.OrderItem;

public class QueryHelper {

	public static List<String> getIdsFromOrderItem(List<OrderItem> order) {
		return order.stream().map(item -> item.getItemId())
				.collect(Collectors.toList());
	}
}
