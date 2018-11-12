var ws = new WebSocket("ws://localhost:8080/WebSocket/ws123");
ws.onmessage = function(e) {
	var json = JSON.parse(e.data);
	
	if(json.type == 'list_room'){
		
		//console.log(json.data);
		
		s = "";
		
		json.data.forEach((room)=>{
			
			s += "<div class=\"room\"><button onclick=\"join_room('"+room.roomID+"')\">Vào phòng</button> <b>"+room.name+"</b></div>";
			
		});
		
		$("#list_room").html(s);
		
		
	} else if(json.type == 'room_update'){
		$('#d_cv .user').html(json.data);
	} else if(json.type == 'join_room'){
		$('#d_cv .title b').html(json.roomName);
		$('#select_room').hide();
		$('#d_cv').show();
		reload();
	} else if(json.type == 'update_pos'){
		tanks[json.uuid].x = json.pos[0];
		tanks[json.uuid].y = json.pos[1];
		tanks[json.uuid].direction = json.pos[2];
		reload();
	} else if(json.type == 'start'){
		tanks = json.tank;
		reload();
	} else if(json.type == 'shoot'){
		shoot(json.x, json.y, json.direction, json.bullet_color);
		reload();
	} else if(json.type == 'win'){
		swal("Bạn đã chiến thắng!", "", "success");
	} else if(json.type == 'loose'){
		swal("Bạn đã thua!", "", "error");
	}
}

function send_msg(data) {
	if(ws.readyState === WebSocket.OPEN) {
		ws.send(JSON.stringify(data));
		return true;
	}
	return false;
}