import{u as P,r as v,c as y,a as k,b as u,d as s,e as l,w as r,v as d,t as g,f as h,g as x,o as p,i as V}from"./index-D-3yqlxH.js";import{a as S}from"./authApi-DADoywrd.js";import"./index-DCcLPtXZ.js";const _={class:"mt-5 mx-auto",style:{width:"500px"}},B={class:"mb-3"},I={class:"mb-3"},N={class:"mb-3"},U={key:0,class:"text-danger"},A=["disabled"],E={__name:"ChangePasswordPage",setup(C){const w=V(),m=P(),a=v({username:m.username,oldPassword:"",newPassword:"",newPassword2:""}),c=y(()=>!a.oldPassword||!a.newPassword||!a.newPassword2),e=k(""),f=()=>e.value="",n=()=>e.value="",b=async()=>{if(a.newPassword!=a.newPassword2){e.value="새 비밀번호가 일치하지 않습니다.";return}try{await S.changePassword(a),alert("비밀번호를 수정했습니다."),w.push({name:"profile"})}catch(i){e.value=i.response.data}};return(i,o)=>(p(),u("div",_,[o[7]||(o[7]=s("h1",{class:"my-5"},[s("i",{class:"fa-solid fa-lock"}),l(" 비밀번호 변경 ")],-1)),s("form",{onSubmit:x(b,["prevent"])},[s("div",B,[o[3]||(o[3]=s("label",{for:"password",class:"form-label"},[s("i",{class:"fa-solid fa-lock"}),l(" 이전 비밀번호: ")],-1)),r(s("input",{type:"password",class:"form-control",placeholder:"이전 비밀번호","onUpdate:modelValue":o[0]||(o[0]=t=>a.oldPassword=t),onInput:f},null,544),[[d,a.oldPassword]])]),s("div",I,[o[4]||(o[4]=s("label",{for:"password",class:"form-label"},[s("i",{class:"fa-solid fa-lock"}),l(" 새 비밀번호: ")],-1)),r(s("input",{type:"password",class:"form-control",placeholder:"새 비밀번호","onUpdate:modelValue":o[1]||(o[1]=t=>a.newPassword=t),onInput:n},null,544),[[d,a.newPassword]])]),s("div",N,[o[5]||(o[5]=s("label",{for:"password",class:"form-label"},[s("i",{class:"fa-solid fa-lock"}),l(" 새 비밀번호 확인: ")],-1)),r(s("input",{type:"password",class:"form-control",placeholder:"새 비밀번호 확인","onUpdate:modelValue":o[2]||(o[2]=t=>a.newPassword2=t),onInput:n},null,544),[[d,a.newPassword2]])]),e.value?(p(),u("div",U,g(e.value),1)):h("",!0),s("button",{type:"submit",class:"btn btn-primary mt-4",disabled:c.value},o[6]||(o[6]=[s("i",{class:"fa-solid fa-check"},null,-1),l(" 확인 ")]),8,A)],32)]))}};export{E as default};
