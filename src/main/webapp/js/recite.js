/*将  所有单词 localWord
	待背诵单词名 wordArray
	单词所有翻译 wordTrans
	当前背诵单抽 loadWord
从本地内存中取出来*/
var localWord = JSON.parse(localStorage.getItem("word"));
var wordArray = JSON.parse(localStorage.getItem("wordArray"));
var wordTimes = JSON.parse(localStorage.getItem("wordTimes"));
var wordTrans = JSON.parse(localStorage.getItem("wordTrans"));
var loadWord = localStorage.getItem("loadWord");

$(document).ready(function(){
	var callback = function(result){
		if(result.success){
			localWord = {}
			wordArray = []
			wordTimes = {}
			wordTrans = []
			
			//console.info(result.results)
			var results = result.results
			localWord = results
			for(key in results){
				wordArray.push(key)
				wordTimes[key] = results[key].times
				var resultTrans = results[key].trans
				for(var loopI in resultTrans){
					var resultTran = resultTrans[loopI]
					wordTrans.push(resultTran.part+resultTran.means)
				}
				
			}
			
			//本地缓存数据
			//原始单词数据
			localStorage.setItem("word",JSON.stringify(localWord))
			//只有单词名称数组
			localStorage.setItem("wordArray",JSON.stringify(wordArray))
			localStorage.setItem("wordTimes",JSON.stringify(wordTimes))
			//当前要背单词
			localStorage.setItem("loadWord",wordArray[0])
			//所有翻译数组
			localStorage.setItem("wordTrans",JSON.stringify(wordTrans))
			loadWord = wordArray[0]
			loadPage();
		}
	}
	
	//缓存中没有单词数据时，向服务器请求数据
	if(localWord == null || localWord == "" || localWord == "undefine"){
		$.ajax({
			type:"post",
			url:"http://localhost:8080/sshBase/app/word/",
			async:false,
			success:callback,
			dataType:"json",
		});
	}else{
		loadPage();
	}
});


function loadPage(){
	$(".word_txt").html(loadWord)
	var now_word = localWord[loadWord]
	var now_trans = now_word.trans
	var now_phonetics = now_word.phonetics
	var now_times = wordTimes[loadWord].times
	var trans_array = []
	var true_tran = now_word.trans[0].part+now_word.trans[0].means
	
	//把现在单词的翻译放在一个数组中
	var now_trans_array = []
	for(var i in now_trans){
		now_trans_array.push(now_trans[i].part+now_trans[i].means)
	}
	
	//在所有翻译中取三个
	var i = 0
	do{
		var rondom = RandomNum(0,wordTrans.length)
		if(now_trans_array.indexOf(wordTrans[rondom])==-1){
			trans_array.push(wordTrans[rondom])
			now_trans_array.push(wordTrans[rondom])
			++i
		}
	}while(i !== 3)
	
	trans_array.push(true_tran)
	//对翻译随机排序
	trans_array.sort(randomSort);
	
	var li_html = ""
	$(".choose_group").html("")
	for(var li_tran in trans_array){
		li_html = '<li class="list-group-item tran_li" >'+trans_array[li_tran]+'</li>'
		$(".choose_group").append(li_html);
		
	}
	$(".tran_li").bind("click",function(){
		var trueText = this.innerText
		console.info(this.innerText)
		
		//总次数+1
		
		if(true_tran === trueText){
			//回答正确的时候
			//正确次数+1
			//loadWord 变到下一个
			var i = wordArray.indexOf(loadWord)
			loadWord = wordArray[++i]
			loadPage()
			
			//判断是否已经正确10次
			//正确十次
			//wordArray  localWord wordTimes 移除该单词
		}else{
			$(".recite_page").hide()
			$(".detail_page").show()
			//错误次数+1
			//显示单词详情
		}
		
	})
	
	loadDetailPage();
};

//详情
function loadDetailPage(){
	$(".detail_word_txt").html(loadWord)
	
	console.info(localWord)
	
	var results = localWord[loadWord]
	
	var trans = results.trans
	var word_name = loadWord
	var phonetics = results.phonetics
	var ph_other = phonetics.ph_other
	var ph_en = phonetics.ph_en
	var ph_en_mp3 = phonetics.ph_en_mp3
	var ph_other_mp3 = phonetics.ph_other_mp3
	var ph_am = phonetics.ph_am
	var ph_am_mp3 = phonetics.ph_am_mp3
	
	$(".en-mp3").attr("src","")
	$(".am-mp3").attr("src","")
	$(".other-mp3").attr("src","")
	$(".en-ph").html("无")
	$(".am-mp3").html("无")
	//$(".other").html("无")

	var en_ph_html = '英音'
	if(ph_en != null && ph_en!= ''){
		en_ph_html += '['+ph_en+']'
	}
	if(ph_en_mp3 != null && ph_en_mp3 !== ''){
		en_ph_html += '<div class="glyphicon glyphicon-volume-up"></div>'
		$(".en-mp3").attr("src",ph_en_mp3)
	}
	
	var am_ph_html = '美音'
	if(ph_am != null && ph_am!= ''){
		am_ph_html += '['+ph_am+']'
	}
	if(ph_am_mp3 != null && ph_am_mp3 !== ''){
		am_ph_html += '<div class="glyphicon glyphicon-volume-up"></div>'
		$(".am-mp3").attr("src",ph_am_mp3)
	}
	
	if(ph_en_mp3 == "undefined" && ph_am_mp3 == "undefined" && ph_other_mp3 != "undefined"){
		en_ph_html += '<div class="glyphicon glyphicon-volume-up"></div>'
		$(".en-mp3").attr("src",ph_other_mp3)
	}
	
	if(en_ph_html === "英音"){
		en_ph_html = "无"
	}
	if(am_ph_html === "美音"){
		am_ph_html = "无"
	}
	$(".en-ph").html(en_ph_html)
	$(".am-ph").html(am_ph_html)
	$(".en-ph").show()
	$(".am-ph").show()
	$(".content").show()
	
	var appendHtml = ""
	for(var i in trans){
		var tran = trans[i]
		appendHtml += '<li class="list-group-item">'+tran.part+tran.means+'</li>'
	}
	$(".detail_trans").html(appendHtml)
	$(".content-trans").show()
}

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


$(".detail_word").bind("click",function(){
	$(".recite_page").show()
	$(".detail_page").hide()
})

$(".content-trans").bind("click",function(){
	$(".recite_page").show()
	$(".detail_page").hide()
})


function RandomNum(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    var num = Min + Math.floor(Rand * Range); //舍去
    return num;
}

function randomSort(a, b) {
	return Math.random() > 0.5 ? -1 : 1;
}

