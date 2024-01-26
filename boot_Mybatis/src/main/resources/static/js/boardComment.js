console.log("board comment js.in")
console.log("bnoVal" ,bnoVal);
document.getElementById('cmtPostBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText');
    if(cmtText.value ==null ||cmtText.value == ''){
        alert('댓글을 입력해주세요');
        cmtText.focus();
        return false;
    }else{
        let cmtData={
            bno:bnoVal,
            writer:document.getElementById('cmtWriter').innerText,
            content:cmtText.value
        };
        console.log(cmtData);
        postCommentToServer(cmtData).then(result=>{
            if(result == '1'){
                alert('댓글등록');
                cmtText.value = "";
            }
            spreadCommentList(cmtData.bno);
        })

    }
})

async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config = {
            method:"post",
            headers : {
                'content-type':'application/json; charset=utf-8'
            },
            body:JSON.stringify(cmtData)
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function getCommentListFromServer(bno){
    try {
        const resp = await fetch("/comment/"+bno);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

function spreadCommentList(bno){
    getCommentListFromServer(bno).then(result=>{
        
        const ul = document.getElementById('cmtListArea');
        if(result.length > 0){
           
                ul.innerHTML ='';
            
            for(let cvo of result){
                let li=`<li class="list-group-item" data-cno="${cvo.cno}" >`;
                li += `<div class="mb-3">`;
                li += `<div class="fw-bold">${cvo.writer}</div>`;
                li += `${cvo.content}`;
                li += `</div>`;
                li += `<span class="badge rounded-pill text-bg-warning">${cvo.modAt}</span>`;
                li += `<button type="button" class="btn btn-sm btn-outline-success mod" data-bs-toggle="modal" data-bs-target="#myModal">Eidt</button>`;
                li += `<button type="button" class="btn btn-sm btn-outline-danger cmtDelBtn">Delete</button>`;
                li += `</li>`;
                ul.innerHTML += li;
            }
        }else{
            let li =`<li class="list-group-item">Comment List Empty</li>`;
            ul.innerHTML = li;
        }
    })
}

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('mod')){
        //댓글 수정 
        //타겟에서 가장 가까운 li 찾기 : 내 버튼이 포함되어있는 li 찾기
        let li = e.target.closest('li');
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;
        document.getElementById('cmtModBtn').setAttribute("data-cno",li.dataset.cno);
    }else if(e.target.id=='cmtModBtn'){
        
        //모달수정 버튼
        let cmtDataMod={
            cno:e.target.dataset.cno,
            content:document.getElementById('cmtTextMod').value
        };

        editCommentToServer(cmtDataMod).then(result =>{
            if(result ==="1"){
                alert("수정완료");
                document.querySelector(".btn-close").click();
            }
            spreadCommentList(bnoVal);
        })
    }else if(e.target.classList.contains('cmtDelBtn')){
        //삭제


    }
})

async function editCommentToServer(cmtDataMod){
    try {
        const url = "/comment/edit";
        const config = {
            method:'put',
            headers:{
                'content-type':'application/json; charset=utf-8'
            },
            body:JSON.stringify(cmtDataMod)
        };
        const resp = await fetch(url, config);
        const result = resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}
