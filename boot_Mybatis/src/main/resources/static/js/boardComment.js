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

async function getCommentListFromServer(bno,page){
    try {
        const resp = await fetch("/comment/"+bno+"/"+page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

function spreadCommentList(bno,page=1){
    getCommentListFromServer(bno,page).then(result=>{
        console.log(result)
        const ul = document.getElementById('cmtListArea');
        if(result.cmtList.length > 0){ //cmtList는 ph에 private으로 들어갔음 (댓글 페이지 처리를 위해)
           if(page == 1){ //1페이지
                ul.innerHTML ='';
           }
            for(let cvo of result.cmtList){
                let li=`<li class="list-group-item" data-cno="${cvo.cno}" data-writer="${cvo.writer} >`;
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
            //댓글 page처리 
            let moreBtn = document.getElementById('moreBtn');
            //현재 페이지 번호가 전체 페이지 번호보다 작다면
            //아직 나와야 할 페이지가 더 있다면 
             if(result.pgvo.pageNo < result.endPage){ //숨김 속성 해제 , 페이지 +1
                moreBtn.style.visibility = "visible";
                moreBtn.dataset.page = page+1;
             }else{ //더 나와야할 페이지(댓글)이 없으면 다시 숨김
                //안하면 위에 if가 돌아가서 더보기버튼 누르면 댓글이 반복적으로 나옴 
                moreBtn.style.visibility="hidden";
             }
        }else{ //댓글이 없으면 Comment List Empty 문구 나옴
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
        document.getElementById('cmtModBtn').setAttribute("data-writer",li.dataset.writer);
    }else if(e.target.id=='cmtModBtn'){
        
        //모달수정 버튼
        let cmtDataMod={
            cno:e.target.dataset.cno,
            writer:e.target.dataset.writer,
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
        let li = e.target.closest('li');
        let cnoVal = li.dataset.cno;

        deleteCommentFromServer(cnoVal).then(result=>{
            console.log("delete Comment cnoVal >>>> ",cnoVal);
            if(result == 1){
                alert("댓글 삭제 완료");
                spreadCommentList(bnoVal);
            }
        })

    }else if(e.target.id=='moreBtn'){
        spreadCommentList(bnoVal,parseInt(e.target.dataset.page))
        
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

async function deleteCommentFromServer(cno){
    try {
        const url='/comment/del/'+cno;
        const config ={
            method : 'delete'
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}