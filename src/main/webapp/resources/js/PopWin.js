/*
*参数：id---必填，width---弹出层的宽度，（可选)，title---弹出层的标题，（可选）
* */
var PopWin = function (data) {
    this.mainid = data.id;
    if(data.title==undefined)
    {
        data.title="提示";
    }
    this.width=data.width;
    this.height=data.height;
    this.title = data.title;
    this.url=data.url;
    this.Initialize();
};
PopWin.prototype = {
    Initialize: function() {
        $("body").append("<div id='GreyCover"+this.mainid+"'></div>");
        $("#GreyCover"+this.mainid).css({
            position: "fixed",
            top: "0",
            left: "0",
            width: "100%",
            height: "100%",
            backgroundColor: "#000000",
            zIndex: "99",
            opacity: "0.5",
            filter: 'Alpha(opacity=50)'
        });
        $("#GreyCover"+this.mainid).hide();
        $("body").append("<div id='Senddiv"+this.mainid+"' class='Senddiv'><div id='SendDiaog"+this.mainid+"'></div></div>");
        $("#SendDiaog"+this.mainid).append("<div class='Dialotitle'><div class='Dialoname'>" + this.title + "</div><div class='closeimg'>×</div></div><div id='content"+this.mainid+"'></div>");
        if(this.mainid!=undefined){
            $("#content"+this.mainid).append($("#" + this.mainid).clone().show());
        }
        if(this.url!=undefined){
            $("#content"+this.mainid).load(this.url);
        }
        if(this.width!=undefined){
            $("#SendDiaog"+this.mainid).css({width:this.width});
        }
        $("#" + this.mainid).remove();
        $(".Dialotitle").css({
            width: "auto",
            backgroundColor: "#1e78d3",
            height:"32px",
            lineHeight:"32px",
            color:"#fff"
        });
        $(".Senddiv").css({
            position:"fixed",
            left:"50%",
            top:"50%",
            zIndex:"100"
        });
        $(".Dialotitle .Dialoname").css({
            float:"left",
            fontSize:"14px",
            paddingLeft:"20px"
        });
        $(".closeimg").css({
            float:"right",
            fontFamily:"宋体",
            fontSize:"18px",
            fontWeight:"bold",
            paddingRight:"8px",
            cursor:"pointer",backgroundImage:"none",marginTop:0
        });
        $("#SendDiaog"+this.mainid).css({
            position: "relative",
            backgroundColor: "white",
            border:"1px solid #1e78d3"
        });
        
        $("#Senddiv"+this.mainid).hide();
        this.bindclick();
    },
    Open: function() {
        $("#GreyCover"+this.mainid).show();
        $("#Senddiv"+this.mainid).fadeIn(600);
        
//        var frame = $("#blank");
//        var dd = frame.context;
//        var hei = dd.body.scrollTop;
        
//        $("#SendDiaog"+this.mainid).css("position","none");
//        var top = $("#SendDiaog"+this.mainid).parent().css('top') - $("#Senddiv"+this.mainid).height()/2;
//        var left = $("#SendDiaog"+this.mainid).parent().css('left') - $("#Senddiv"+this.mainid).width() / 2;
//        $("#SendDiaog"+this.mainid).parent().css('top',top);
//        $("#SendDiaog"+this.mainid).parent().css('left',left);
        $("#SendDiaog"+this.mainid).css("top", -$("#Senddiv"+this.mainid).height() / 2 - 10 + "px");
        $("#SendDiaog"+this.mainid).css("left", -$("#Senddiv"+this.mainid).width() / 2 + "px");
    },
    Close: function() {
        $("#GreyCover"+this.mainid).hide();
        $("#Senddiv"+this.mainid).fadeOut(500, function() {
            $("#Senddiv"+this.mainid).hide();
        });
    },
    bindclick: function() {
        var self = this;
        $("#SendDiaog"+this.mainid).find(".closeimg").click(function() {
            self.Close();
        });
    }
};