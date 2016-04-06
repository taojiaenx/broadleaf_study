function categoryItemOnclick(event)
{
	var targetElement = evet.target;
	for(category in $('.categoryItems')) {
		if (category != targetElement) {
			category.className += 'active_category';
		} else {
			category.className -= 'active_category';
		}
	}
}