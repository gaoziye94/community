/**
 * 发布评论
 */
function comment(e,type){
    postComment(e,type)
}

/**
 * 点赞
 * @param data
 */
function likeCount(e) {
    var commenId = e.getAttribute("data-id");
   likeCommentAjax(commenId,e);
}

/**
 * 二级评论
 */
function showApply(e) {
    var id = e.getAttribute("data-id")
    var comment = $('#comment-'+id)
    comment.toggleClass("in");
    if(comment.hasClass('in')){
        //发起请求，获取评论回复，二级评论
        replyList(id,e)
    }
}

// // 评论和取消按钮的显示和隐藏
// function onFocus(e) {
//     var inputId = e.getAttribute('data');
//     $('#aw-comment-box-btn-'+inputId).show();
// }
// function onBlur (e) {
//     var inputId = e.getAttribute('data');
//     //$('#reply-'+inputId).val('');
//     $('#aw-comment-box-btn-'+inputId).hide();
// }

function replyList(id,e) {
    $.ajax({
        type:"get",
        url:"/comment/reply",
        data:{"id":id},
        dataType:"JSON",
        success:function (data) {
            var $comment = $('#comment-'+id).find('.bs-example')
            $comment.empty();
            if(data.length == 0){
                var html = '<div align="center" class="aw-padding10">暂无评论</div>';
                $comment.append(html)
            } else {
                var ulDom=document.createElement("ul");
                ulDom.className = 'media-list';
                for (var i = 0; i < data.length; i++) {
                    var name = data[i].name;
                    var headUrl = data[i].head_url;
                    var content = data[i].content;
                    var gmt_modified = data[i].gmt_modified;
                    var text = "";
                    text += "<div class=\"media-left\">";
                    text += "	<a href=\"#\">";
                    text += "		<img class=\'media-object\' src="+headUrl+ "alt=\"...\">";
                    text += "	</a>";
                    text += "</div>";
                    text += "<div class=\"media-body\">";
                    text += "	<div class=\"media-heading\">"+name+"</div>";
                    text += "	<p>"+content+"<span style='float: right;'>"+new Date(gmt_modified).Format('yy-MM-dd hh:mm');+"</span></p>";
                    text += "</div>";
                    var liDom=document.createElement("li");
                    liDom.className = 'media';
                    liDom.innerHTML=text;
                    ulDom.appendChild(liDom)
                }
                $comment.append(ulDom)
            }
        }
    })
}
/**
 * 发布评论ajax请求
 */
function postComment(e,type) {
    var parentId;
    var content;
    if(type == 1){
       parentId = $('#questionId').val();
       content = $('.commentcontent').val();
        if(content ==null || content.trim() ==""){
            alert("请输入评论内容")
            return;
        }
    } else if(type == 2){
        parentId=e.getAttribute("data");
        content = $('#reply-'+parentId).val();
    }
    var commentObj ={
        "parentId":parseInt(parentId),
        "content":content,
        "type":type,
    }
    extracted();
    function extracted() {
        $.ajax({
            type: "POST",
            url: "/comment/insert",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(commentObj),
            dataType: "JSON",
            success: function (response) {
                if (response.type == "success") {
                    if(type ==1){
                        alert('发表成功')
                        $('#commentArea').hide();
                    } else if(type ==2){
                        //增加到二级评论列表
                       var $ul =  $('#comment-'+parentId).find('ul');
                        alert('发表成功')
                    }
                    window.history.go(0)
                } else {
                    if (response.code == "20000") {
                        //未登录
                        var flag = confirm(response.msg + ",确定要登录吗？");
                        if (flag) {
                            window.location.href = '/account/login';
                        }
                    } else {
                        alert(response.msg)
                    }
                }
            }
        })
    }
}

function likeCommentAjax(commenId,data) {
    $.ajax({
        type:'post',
        url:'/comment/like',
        data:{"id":commenId},
        dataType:"JSON",
        success:function (msg) {
            if(msg.type == 'success'){
                var likeCount = msg.content.likeCount;
                console.log(likeCount)
                var oldLike = $(data).find('likeCount').text();
                console.log(oldLike)
                $(data).find('.likeCount').text(likeCount)
            } else {
                alert(msg.msg)
            }
        },
        error:function () {

        },
    })
}


