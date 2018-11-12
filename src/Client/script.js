var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");

var tank_icon_1 = new Image();
var tank_icon_2 = new Image();
var tank_icon_3 = new Image();
var tank_icon_4 = new Image();
var blue_tank_icon_1 = new Image();
var blue_tank_icon_2 = new Image();
var blue_tank_icon_3 = new Image();
var blue_tank_icon_4 = new Image();
var red_tank_icon_1 = new Image();
var red_tank_icon_2 = new Image();
var red_tank_icon_3 = new Image();
var red_tank_icon_4 = new Image();


blue_tank_icon_1.src = "images/blue_tank_icon_1.png";
blue_tank_icon_2.src = "images/blue_tank_icon_2.png";
blue_tank_icon_3.src = "images/blue_tank_icon_3.png";
blue_tank_icon_4.src = "images/blue_tank_icon_4.png";

red_tank_icon_1.src = "images/red_tank_icon_1.png";
red_tank_icon_2.src = "images/red_tank_icon_2.png";
red_tank_icon_3.src = "images/red_tank_icon_3.png";
red_tank_icon_4.src = "images/red_tank_icon_4.png";

tank_icon_1.src = "images/tank_icon_1.png";
tank_icon_2.src = "images/tank_icon_2.png";
tank_icon_3.src = "images/tank_icon_3.png";
tank_icon_4.src = "images/tank_icon_4.png";

var bullet = [];
var tanks = [];
var shot_arr = [];
var map = [ 
   [0,1,1,0,0,0,0,1,0,0],
   [0,0,0,0,0,0,0,1,0,1],
   [1,1,1,1,1,1,0,1,0,0],
   [0,0,0,0,0,1,0,1,0,1],
   [0,1,1,1,0,1,0,0,0,0],
   [0,0,0,0,0,0,0,1,0,1],
   [0,0,0,0,0,0,0,0,0,0],
   [0,0,0,0,0,1,0,0,0,0],
   [0,1,0,1,0,0,0,0,0,0],
   [1,1,1,1,1,1,1,1,1,0],
];

//tanks = {"11":{"direction":1 , "x":1, "y":2},"22":{"direction":3 , "x":3, "y":1}};

function draw_tank(){
	//tanks.forEach((tank)=>{
	for(var i in tanks){
		
		var tank = tanks[i];
		if(tank.tank_color == 1){
			if(tank.direction == 1) var img = blue_tank_icon_1;
			if(tank.direction == 2) var img = blue_tank_icon_2;
			if(tank.direction == 3) var img = blue_tank_icon_3;
			if(tank.direction == 4) var img = blue_tank_icon_4;
		} else if(tank.tank_color == 2){
			if(tank.direction == 1) var img = red_tank_icon_1;
			if(tank.direction == 2) var img = red_tank_icon_2;
			if(tank.direction == 3) var img = red_tank_icon_3;
			if(tank.direction == 4) var img = red_tank_icon_4;
		} else {
			if(tank.direction == 1) var img = tank_icon_1;
			if(tank.direction == 2) var img = tank_icon_2;
			if(tank.direction == 3) var img = tank_icon_3;
			if(tank.direction == 4) var img = tank_icon_4;
		}
		/* img.onload = function() {
			ctx.drawImage(img, tank.x*60, tank.y*60, 60, 60);
		} */
		ctx.drawImage(img, tank.x*60, tank.y*60, 60, 60);	
	}
}


function draw_grass(){
	for(i=0;i<10;i++){
		for(j=0;j<10;j++){
			if(map[i][j] == 1){
				ctx.fillStyle = "#005710";
				ctx.fillRect(j*60, i*60, 60, 60);
			}
		}
	}
}


function draw_bullet(){
	bullet.forEach(function(bl){
		//console.log(bl[2]);
		if(bl[2] == 1){
			ctx.fillStyle = "#00F";
		} else if(bl[2] == 2){
			ctx.fillStyle = "#F00";
		} else {
			ctx.fillStyle = "#000";
		}
		ctx.fillRect(bl[0],bl[1],8,8);
	});
}

function reload(){
	ctx.fillStyle = "#EEE";
	ctx.fillRect(0, 0, c.width, c.height);
	draw_tank();
	draw_bullet();
	draw_grass();
}








function shoot(x, y, _direction, bullet_color){
	shot_id = shot_arr.length;
	bullet[shot_id] = [];
	
	bullet[shot_id][0] = x*60+26;
	bullet[shot_id][1] = y*60+26;
	bullet[shot_id][2] = bullet_color;
	shot_arr[shot_id] = setInterval(bullet_interval, 30, shot_id, _direction);
}

function bullet_interval(s_id, drt){
	if(drt == 1){
		bullet[s_id][1] -= 10;
	}
	if(drt == 2){
		bullet[s_id][0] += 10;
	}
	if(drt == 3){
		bullet[s_id][1] += 10;
	}
	if(drt == 4){
		bullet[s_id][0] -= 10;
	}
	if(bullet[s_id][0] > 600 || bullet[s_id][0] < -60 || bullet[s_id][1] > 600 || bullet[s_id][1] < -60){
		clearInterval(shot_arr[s_id]);
	}
	reload();
}

ctx.fillStyle = "#EEE";
ctx.fillRect(0, 0, c.width, c.height);


onkeydown = function(e){
    e = e || event;
	
	if(e.keyCode == 32 || e.keyCode == 37 || e.keyCode == 38 || e.keyCode == 39 || e.keyCode == 40){
		send_msg({type: "keypress", key: e.keyCode+""});
	}
	
};

document.addEventListener('keypress', onkeydown);