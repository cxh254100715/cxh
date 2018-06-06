layui.use('layer', function(){
    var $ = layui.$;
    var pendingRequests = {};
    $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
        var key = options.url+JSON.stringify(originalOptions.data);
        console.log(key);
        if (!pendingRequests.hasOwnProperty(key)) {
            console.log(pendingRequests[key]);
            pendingRequests[key] = jqXHR;
        } else {
            console.log("重复提交");
            // jqXHR.abort(); //放弃后触发的提交
            jqXHR.abort(); // 放弃先触发的提交
        }
        var complete = options.complete;
        options.complete = function(jqXHR, textStatus) {
            pendingRequests[key] = null;
            if ($.isFunction(complete)) {
                complete.apply(this, arguments);
            }
        };
    });
})