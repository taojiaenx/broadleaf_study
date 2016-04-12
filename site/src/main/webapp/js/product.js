function checkLength(element) {
    var noteLabel = document.getElementById('watermark');
    var nowLabel = document.getElementById('blessingShow');
    var wordCount = document.getElementById('wordCount');
    var nowText = element.value;
    var nowlength =nowText.length;
    if (nowlength > 0) {
        nowLabel.innerHTML=nowText;
        noteLabel.style.display = 'none';
    } else{
        nowLabel.innerHTML='您的祝福语将显示在这里';
        noteLabel.style.display ='block';
    }

    wordCount.innerHTML = ( new Number(30 - parseInt(nowlength).toString()));
}