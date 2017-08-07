var localWord = JSON.parse(localStorage.getItem("word"));
var wordArray = JSON.parse(localStorage.getItem("wordArray"));
//var wordTimes = JSON.parse(localStorage.getItem("wordTimes"));
var wordTrans = JSON.parse(localStorage.getItem("wordTrans"));
var loadWord = localStorage.getItem("loadWord");

$(document).ready(function(){
	var callback = function(result){
		if(result.success){
			localWord = {}
			wordArray = []
			wordTimes = {}
			wordTrans = []
			
			console.info(result.results)
			var results = result.results
			localWord = results
			for(key in results){
				wordArray.push(key)
				//wordTimes[key] = results[key].times
				var resultTrans = results[key].trans
				for(var loopI in resultTrans){
					var resultTran = resultTrans[loopI]
					wordTrans.push(resultTran.part+resultTran.means)
				}
				
			}
			localStorage.setItem("word",JSON.stringify(localWord))
			localStorage.setItem("wordArray",JSON.stringify(wordArray))
			//localStorage.setItem("wordTimes",JSON.stringify(wordTimes))
			localStorage.setItem("loadWord",wordArray[0])
			localStorage.setItem("wordTrans",JSON.stringify(wordTrans))
			loadWord = wordArray[0]
			loadPage();
		}
	}
	
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
	var now_times = now_word.times
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
	for(var li_tran in trans_array){
		li_html += '<li class="list-group-item tran_li">'+trans_array[li_tran]+'</li>'
	}
	$(".choose_group").append(li_html);
	
	$(".tran_li").bind("click",function(){
		console.info($(".tran_li").val())
	})
	
};


function RandomNum(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    var num = Min + Math.floor(Rand * Range); //舍去
    return num;
}

function randomSort(a, b) {
	return Math.random() > 0.5 ? -1 : 1;
}
