import{a as m,u as _,l as f,r as p,i as b,o as h,d as v,e as t,w as r,v as l,j as w,k as x,q as y,h as k,F as q,g as a}from"./index-I6TbVDK2.js";import{b as V}from"./boardApi-DIdKxdX5.js";import"./index-CFW9R_hc.js";const g=t("h1",null,[t("i",{class:"fa-regular fa-pen-to-square"}),a(" 글 작성")],-1),B={class:"mb-3 mt-3"},C=t("label",{for:"title",class:"form-label"}," 제목 ",-1),N=t("div",{class:"invalid-feedback"},"제목은 필수 요소입니다.",-1),A={class:"mb-3 mt-3"},F=t("label",{for:"content",class:"form-label"}," 내용 ",-1),M={class:"my-5 text-center"},R=t("button",{type:"submit",class:"btn btn-primary me-3"},[t("i",{class:"fa-solid fa-check"}),a(" 확인 ")],-1),S=t("i",{class:"fa-solid fa-list"},null,-1),P={__name:"BoardCreatePage",setup(T){const n=m(),i=_(),c=f(),e=p({writer:n.username}),d=async()=>{confirm("등록할까요?")&&(await V.create(e),i.push("/board/list"))};return(U,s)=>{const u=b("router-link");return h(),v(q,null,[g,t("form",{class:"was-validated",onSubmit:k(d,["prevent"])},[t("div",B,[C,r(t("input",{type:"text",class:"form-control",placeholder:"제목",id:"title","onUpdate:modelValue":s[0]||(s[0]=o=>e.title=o),required:""},null,512),[[l,e.title]]),N]),t("div",A,[F,r(t("textarea",{class:"form-control",placeholder:"내용",id:"content","onUpdate:modelValue":s[1]||(s[1]=o=>e.content=o),rows:"10"},null,512),[[l,e.content]])]),t("div",M,[R,w(u,{class:"btn btn-primary",to:{name:"board/list",query:y(c).query}},{default:x(()=>[S,a(" 목록 ")]),_:1},8,["to"])])],32)],64)}}};export{P as default};
