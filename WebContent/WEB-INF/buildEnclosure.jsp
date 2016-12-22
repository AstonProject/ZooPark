<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/buildEnclosure.css" />
<title>Building_menu</title>
</head>
<body>
	<h1>Building menu</h1>
	<br>
	<div class="enclosureChoice">
		<form action="createEnclosure" method="post" id="FormCreateEnclosure">
			<h3>Enclosure type</h3>
			<br>

			<div id="radio1">
				<input type="radio" name="enclosureType" value="Elephant"
					id="radio_elephant"> <img
					src="${pageContext.request.contextPath}/assets/images/Contruction_menu_elephant.png" alt="logo" /> <input
					type="radio" name="enclosureType" value="Giraffe"
					id="radio_giraffe"> <img
					src="${pageContext.request.contextPath}/assets/images/Contruction_menu_giraffe.png" alt="logo" /> <input
					type="radio" name="enclosureType" value="Lion" id="radio_lion">
				<img src="${pageContext.request.contextPath}/assets/images/Contruction_menu_lion.png" alt="logo" /> <input
					type="radio" name="enclosureType" value="Camel" id="radio_camel">
				<img src="${pageContext.request.contextPath}/assets/images/Contruction_menu_camel.png" alt="logo" />
			</div>
			<br>

			<h3 class="Enclosure-Description-Title">Description</h3>

			<div class="enclosure-Description">
				<div class="elephantDiscription">
					<div class="char-block">
						<h4 id="animal">African elephants</h4>
						One species of African elephant is the largest living terrestrial
						animal. Their thickset bodies rest on stocky legs, and they have
						concave backs. Their large ears enable heat loss. males, bigger
						than femals, stand 4.0 m tall and weigh 5 tonnes.
						<p class="characteristic">Animal characteristic:</p>
						Health: Hight<br>Food Consumption : High<br>
						Aggressiveness: Moderate

						<p class="characteristic">Enclosure characteristic :</p>
						Cost: Huge<br>Maintenance cost: Hight<br>
						Attractiveness: High
					</div>
				</div>

				<div class="giraffeDiscription">
					<div class="char-block">
						<h4 id="animal">Giraffes</h4>
						The giraffe is the tallest terrestrial mammals, and the largest
						ruminants. Fully grown giraffes stand 5 m tall, with males taller
						than females.The average weight is 1.2 tonnes. Despite its long
						neck and legs, the giraffe's body is relatively short. Giraffes'
						senses of hearing and smell are sharp. The animal can close its
						muscular nostrils to protect against sandstorms and ants.
						<p class="characteristic">Animal characteristic:</p>
						Health: low<br>Food Consumption : Moderate<br>
						Aggressiveness: Low

						<p class="characteristic">Enclosure characteristic :</p>
						Cost: Hight<br>Maintenance cost: Hight<br>
						Attractiveness: High
					</div>
				</div>
				<div class="lionDiscription">
					<div class="char-block">
						<h4 id="animal">Lions</h4>
						In the wild, males seldom live longer than 12 years, as injuries
						sustained from continual fighting with rival males greatly reduce
						their longevity. In captivity they can live more than 20 years.
						Lions are apex and keystone predators, although they are also
						expert scavengers obtaining over 50 percent of their food by
						scavenging as opportunity allows. While lions do not typically
						hunt humans, some have done.
						<p class="characteristic">Animal characteristic:</p>
						Health: low<br>Food Consumption : Moderate<br>
						Aggressiveness: Hight

						<p class="characteristic">Enclosure characteristic :</p>
						Cost: Moderate<br>Maintenance cost: Moderate<br>
						Attractiveness: Very high
					</div>
				</div>

				<div class="camelDiscription">
					<div class="char-block">
						<h4 id="animal">Camels</h4>
						The average life expectancy of a camel is 40 to 50 years. A
						full-grown adult camel stands 1.85 m at the shoulder and 2.15 m at
						the hump. Camels can run up to 40 km/h. Bactrian camels weigh 600
						kg.
						<p class="characteristic">Animal characteristic:</p>
						Health: Moderate<br>Food Consumption : Low<br>
						Aggressiveness: Moderate

						<p class="characteristic">Enclosure characteristic :</p>
						Cost: Low<br>Maintenance cost: Low<br> Attractiveness:
						Moderate
					</div>
				</div>
			</div>
			<br>

			<h3>Enclosure size</h3>

			<div id="radio2">
				<input type="radio" name="enclosureSize" value="1"
					id="size_1"> 1 <input type="radio" name="enclosureSize"
					value="2" id="size_2"> 2 <input type="radio"
					name="enclosureSize" value="3" id="size_3"> 3
			</div>
			<div id="getLocate">
			Locate_x:<input type="hidden" name="loc_x" value = "locate_x"/>
        	Locate_y:<input type="hidden" name="loc_y"  value = "locate_y"/>
        	Price:<input type="hidden" name="price"  value = ""/>
        	</div>
			<h3>Price</h3>

			<div class="showPrice">
			<div class="price"></div></div>
			<br> <input type="submit" value="buy" name="validate">
		</form>
		
		
		
		
		
		
	</div>
	<script src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
	<script src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
	<script src="${pageContext.request.contextPath}/assets/scripts/buildEnclosure.js"></script>
</body>
</html>