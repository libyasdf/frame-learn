function initNeko(id){
    var neko = document.querySelector('#'+id);
    var searchBtn = document.querySelector('.search-icon');
    var nekoW = neko.offsetWidth;
    var nekoH = neko.offsetHeight;
    var cuntW = 0;
    var cuntH = 0;
    var startTime = 0;
    // neko.style.left = parseInt( Math.floor((Math.random()*1000)+1)) + 'px';
    // neko.style.top = parseInt( Math.floor((Math.random()*1000)+1)) + 'px';
    function move(obj, w, h) {
        if (obj.direction === 'left') {
            obj.style.left = 0 - w + 'px';
        } else if (obj.direction === 'right') {

            obj.style.left = document.body.offsetWidth - nekoW + w + 'px';
        }
        if (obj.direction === 'top') {
            obj.style.top = 0 - h + 'px';
        } else if (obj.direction === 'bottom') {
            obj.style.top = document.body.offsetHeight - nekoH + h + 'px';
        }
    }

    function rate(obj, a) {
        //  console.log(a);
        obj.style.transform = ' rotate(' + a + ')'
    }

    function action(obj) {

        var dir = obj.direction;

        switch (dir) {
            case 'left':
                rate(obj, '90deg');
                break;
            case 'right':
                rate(obj, '-90deg');
                break;
            case 'top':
                rate(obj, '-180deg');
                break;
            default:
                rate(obj, '-0');
                break;
        }

    }
    function hideSearch(){
        var searchWrapper = document.querySelector('.search-wrapper');
        var searchInput = document.querySelector('.search-input');
        searchWrapper.style.padding = 0;
        searchWrapper.style.width = 0;
        searchInput.style.width = 0;
    }
    function doClick(){
        var searchWrapper = document.querySelector('.search-wrapper');
        var searchInput = document.querySelector('.search-input');
        var serachWidth ;
        if (searchWrapper.currentStyle) {
            serachWidth = searchWrapper.currentStyle.width
        }else{
            serachWidth = getComputedStyle(searchWrapper,null).width
        }
        console.log(serachWidth);

        if (serachWidth == '2px'){  // 展开
            searchWrapper.style.padding = '0 37px';
            searchWrapper.style.width = '227px';
            searchInput.style.width = 'auto';
        } else {  // 收起
            searchWrapper.style.padding = 0;
            searchWrapper.style.width = 0;
            searchInput.style.width = 0;
        }

    }
    neko.onmousedown = function (e) {
        startTime = e.timeStamp;

        var nekoL = e.clientX - neko.offsetLeft;
        var nekoT = e.clientY - neko.offsetTop;
        document.onmousemove = function (e) {
            cuntW = 0;
            cuntH = 0;
            neko.direction = '';
            neko.style.transition = '';
            neko.style.left = (e.clientX - nekoL) + 'px';
            neko.style.top = (e.clientY - nekoT) + 'px';
            if (e.clientX - nekoL < 50) {
                neko.direction = 'left';
                hideSearch()
            }
            if (e.clientY - nekoT < 50) {
                neko.direction = 'top';
                hideSearch()
            }
            if (e.clientX - nekoL > document.body.offsetWidth - nekoW - 50) {
                neko.direction = 'right';
                hideSearch()
            }
            if (e.clientY - nekoT > document.body.offsetHeight - nekoH - 50) {
                neko.direction = 'bottom';
                hideSearch()
            }
            move(neko, 0, 0);
        }
    }
    neko.onmouseover = function () {
        move(this, 0, 0);
        rate(this, 0)
    }

    neko.onmouseout = function () {
        move(this, nekoW / 2, nekoH / 2);
        action(this);
    }

    neko.onmouseup = function (e) {
        document.onmousemove = null;
        this.style.transition = '.5s';
        move(this, nekoW / 2, nekoH / 2);
        action(this);


        return false
    }
    searchBtn.onclick = function(e){
        if (startTime) {
            var diffTime = e.timeStamp - startTime
            diffTime < 150 && doClick() //小于150就执行单击操作
            startTime = 0
        }
        // 阻止事件冒泡
        if(e.preventDefault){
            e.preventDefault();
        }else{
            window.event.returnValue == false;
        }
    }

    window.onresize = function () {
        var bodyH = document.body.offsetHeight;
        var nekoT = neko.offsetTop;
        var bodyW = document.body.offsetWidth;
        var nekoL = neko.offsetLeft;

        if (nekoT + nekoH > bodyH) {
            neko.style.top = bodyH - nekoH + 'px';
            cuntH++;
        }
        if (bodyH > nekoT && cuntH > 0) {
            neko.style.top = bodyH - nekoH + 'px';
        }
        if (nekoL + nekoW > bodyW) {
            neko.style.left = bodyW - nekoW + 'px';
            cuntW++;
        }
        if (bodyW > nekoL && cuntW > 0) {
            neko.style.left = bodyW - nekoW + 'px';
        }

        move(neko, nekoW / 2, nekoH / 2);
    }
}