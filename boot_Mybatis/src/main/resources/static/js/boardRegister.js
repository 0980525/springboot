console.log("board register.js in");

document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('files').click();
});

const regExp = new RegExp("\.(exe|sh|bat|js|dll|msi)$");
// const regExpImg = new ㄲegExp("\.(jpg|jpeg|png|bmp|gif)$");
const maxSize = 1024*1024*20;

function fileValidation(fileName,fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else {
        return 1;
    }

};

document.addEventListener('change',(e)=>{
    if(e.target.id == 'files'){
        //multiple 배열로 들어옴
        const fileObject = document.getElementById('files').files;
        console.log(fileObject);
        document.getElementById('regBtn').disabled = false;
        const div = document.getElementById('filezone');
        //이전에 업로드 했던 파일들이 있다면 제거
        div.innerHTML=""; 

        let ul=`<ul class="list-group list-group-flush" >`;

        //여러파일에 대한 값을 확인하기 위한 값
        let isOk = 1;

        for(let file of fileObject){
            let vaildResult = fileValidation(file.name, file.size);
            //하나씩 모든 파일에 대한 확인
            isOk *= vaildResult;
            ul +=`<li class="list-group-item">`;
            ul+=`<div class="ms-2 me-auto">`;
            ul+=`${vaildResult ? '<div class="fw-bold">업로드 가능' : '<div class="fw-bold text-danger">업로드 불가능'} </div>`;
            ul+=`${file.name}</div>`;
            ul+=`<span class="badge bg-${vaildResult ? 'success':'danger'} rounded-poll">${file.size}Byte</span>`;
            ul+=`</li>`
        }
        ul+=`</ul>`;
        div.innerHTML = ul;

        //파일중 vaild결과에 맞지 않는 값이 있다면
        if(isOk == 0){
            document.getElementById('regBtn').disabled = true;
        }
        //document.getElementById('regBtn').disabled 버튼 비활성화 /비활성화 된 버튼은 false로 스스로 돌아오지 않아서 위에 바꿔줘야했음
    }
})
