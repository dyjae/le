/*$(".input-clear").bind("click",function(e){
	$(".search-input").val("")
	//var input = document.getElementById('search_input')
	//console.info(input)
	window.setTimeout("$('#search-input')[0].focus()", 50);
})*/

document.getElementById('input-clear').addEventListener("click",function(){
	$('.search-input').val("")
	//var input = document.getElementById('search_input')
	//console.info(input)
	window.setTimeout("$('#search-input')[0].focus()", 10)
	//$('.input-clear').hide()
})

$(".search-input").on("mouseup",function(e){
	e.preventDefault()
})

/*$('.input-clear').bind('click',function(e){
	$('.search-input').val("")
	//var input = document.getElementById('search_input')
	//console.info(input)
	window.setTimeout("$('#search-input')[0].focus()", 10)
	$('.input-clear').hide()
})*/

$(".search-input").bind("focus",function(){
	$(".input-clear").show()
})
$(".search-input").bind("blur",function(){
	if($(".search-input").val() == ""){
		$(".input-clear").hide()
	}
})



$(".glyphicon-search").bind("click",function(){
	var word = $(".form-control").val()
	var callBack = function(result){
		if(result.success === true){
			var results = result.results
			var trans = results.trans
			var word_name = results.word_name
			var ph_other = results.ph_other
			var ph_en = results.ph_en
			var ph_en_mp3 = results.ph_en_mp3
			var ph_other_mp3 = results.ph_other_mp3
			var ph_am = results.ph_am
			var ph_am_mp3 = results.ph_am_mp3
			
			$(".en-mp3").attr("src","")
			$(".am-mp3").attr("src","")
			$(".en-ph").html("无")
			$(".am-ph").html("无")

			var en_ph_html = ''
			if(ph_en != null && ph_en!= ''){
				en_ph_html += '英音['+ph_en+']'
			}
			if(ph_en_mp3 != null && ph_en_mp3 !== ''){
				en_ph_html += '<div class="glyphicon glyphicon-volume-up"></div>'
				$(".en-mp3").attr("src",ph_en_mp3)
			}
			
			var am_ph_html = ''
			if(ph_am != null && ph_am!= ''){
				am_ph_html += '美音['+ph_am+']'
			}
			if(ph_am_mp3 != null && ph_am_mp3 !== ''){
				am_ph_html += '<div class="glyphicon glyphicon-volume-up"></div>'
				$(".am-mp3").attr("src",ph_am_mp3)
			}
			
			if(ph_en_mp3 == "undefined" && ph_am_mp3 == "undefined" && ph_other_mp3 != "undefined"){
				en_ph_html += '<div class="glyphicon glyphicon-volume-up"></div>'
				$(".en-mp3").attr("src",ph_other_mp3)
			}
			
			$(".en-ph").html(en_ph_html)
			$(".am-ph").html(am_ph_html)
			$(".content").show()
			
			var appendHtml = ""
			for(var i in trans){
				var tran = trans[i]
				appendHtml += '<li class="list-group-item">'+tran.part+tran.means+'</li>'
			}
			$(".list-group").html(appendHtml)
		}
		window.setTimeout("$('search-input').focus()", 50);
	} 
$.getJSON("http://www.alanjae.com/sshBase/app/search/"+word, callBack);
	//$.getJSON("http://localhost:8080/sshBase/app/search/"+word, callBack);
})

$(".en-ph").bind("click",function(){
	var mp3 = $(".en-mp3").attr("src")
	audio = document.getElementById('enmp3')
	if(mp3!= null && mp3!= ''){
		audio.play()
	}
})

$(".am-ph").bind("click",function(){
	var mp3 = $(".am-mp3").attr("src")
	audio = document.getElementById('ammp3')
	if(mp3!= null && mp3!= ''){
		audio.play()
	}
})

