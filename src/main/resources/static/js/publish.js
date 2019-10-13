/**
 * 点击input框显示所有标签
 */
function showTags() {
    $('.tags').show()
}
/**
 * 选择标签，显示
 * @param tag
 */
function selectTags (e) {
    console.log(e.getAttribute("data"))
   var tag =  e.getAttribute("data")
    if( $('#tags').val() == ''){
        $('#tags').val(tag)
    } else {
        var oldTags = $('#tags').val().split('，');
        if(oldTags.indexOf(tag) > -1){
            //则包含该元素
            alert('该标签已添加')
            return;
        }
        $('#tags').val($('#tags').val() + '，' + tag)
    }
}
