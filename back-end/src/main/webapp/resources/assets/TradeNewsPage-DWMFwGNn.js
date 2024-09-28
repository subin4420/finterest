import{_,T as m,q as y,s as p,b as a,j as l,d as t,w as v,v as f,x as w,f as d,F as N,p as T,o as r,t as c,l as h}from"./index-D-3yqlxH.js";const k={name:"CommunityPage",components:{TradeImage:m,TradeNavigationBar:y},data(){return{articles:[],loading:!0,searchQuery:"경제"}},async mounted(){await this.fetchNews()},methods:{async fetchNews(){const o=this.getYesterdayDate();try{const s=await p.get(`https://newsapi.org/v2/everything?q=${this.searchQuery}&from=${o}&sortBy=popularity&apiKey=ab5fa71416bc4687a2c535d3606c3f91`);this.articles=s.data.articles}catch(s){console.error("Error fetching news:",s)}finally{this.loading=!1}},searchNews(){this.loading=!0,this.fetchNews()},getYesterdayDate(){const o=new Date;return o.setDate(o.getDate()-1),o.toISOString().split("T")[0]}}},I={class:"community-page"},x={class:"search-container"},B={class:"news-container"},D={key:0,class:"loading"},b={key:1},C={key:0,class:"no-results"},Q={class:"articles-container"},V={class:"article-header"},K=["src"],S={class:"article-info"},q={class:"author"},E=["href"];function F(o,s,L,P,n,i){const u=h("TradeImage"),g=h("TradeNavigationBar");return r(),a("div",I,[l(u),l(g),t("div",x,[v(t("input",{type:"text","onUpdate:modelValue":s[0]||(s[0]=e=>n.searchQuery=e),placeholder:"검색어를 입력하세요",onKeyup:s[1]||(s[1]=w((...e)=>i.searchNews&&i.searchNews(...e),["enter"]))},null,544),[[f,n.searchQuery]]),t("button",{onClick:s[2]||(s[2]=(...e)=>i.searchNews&&i.searchNews(...e))},"검색")]),t("div",B,[s[3]||(s[3]=t("h2",null,"검색 결과",-1)),n.loading?(r(),a("div",D,"Loading...")):(r(),a("div",b,[n.articles.length===0?(r(),a("div",C," 검색 결과가 없습니다. ")):d("",!0),t("div",Q,[(r(!0),a(N,null,T(n.articles,e=>(r(),a("div",{key:e.url,class:"article"},[t("div",V,[e.urlToImage?(r(),a("img",{key:0,src:e.urlToImage,alt:"Article Image",class:"article-image"},null,8,K)):d("",!0),t("div",S,[t("p",q,c(e.author||"작성자 없음"),1),t("h3",null,c(e.title),1),t("p",null,c(e.description),1),t("a",{href:e.url,target:"_blank",class:"read-more"},"Read more",8,E)])])]))),128))])]))])])}const j=_(k,[["render",F],["__scopeId","data-v-c78ed240"]]);export{j as default};
