<template>
    <div>
      <header>Admin Panel</header>
      <aside>
        <nav>
          <router-link to="/users">Users</router-link>
          <router-link to="/archives">Archives</router-link>
          <router-link to="/quizzes">Quizzes</router-link>
        </nav>
      </aside>
      <main>
        <slot />
      </main>
    </div>
  </template>
  