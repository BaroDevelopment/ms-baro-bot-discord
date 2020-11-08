<template>
  <div class="sidebar-button" @click="$emit('toggle-sidebar')">
    <svg
        aria-hidden="true"
        class="icon"
        role="img"
        viewBox="0 0 448 512"
        xmlns="http://www.w3.org/2000/svg"
    >
      <path
          class=""
          d="M436 124H12c-6.627 0-12-5.373-12-12V80c0-6.627 5.373-12 12-12h424c6.627 0 12 5.373 12 12v32c0 6.627-5.373 12-12 12zm0 160H12c-6.627 0-12-5.373-12-12v-32c0-6.627 5.373-12 12-12h424c6.627 0 12 5.373 12 12v32c0 6.627-5.373 12-12 12zm0 160H12c-6.627 0-12-5.373-12-12v-32c0-6.627 5.373-12 12-12h424c6.627 0 12 5.373 12 12v32c0 6.627-5.373 12-12 12z"
          fill="currentColor"
      />
    </svg>
  </div>
</template>

<style lang="stylus" scoped>
.sidebar-button
cursor pointer
display none
width

1.25
rem
height

1.25
rem
position absolute
padding

0.6
rem
top

0.6
rem
left

1
rem
.icon
display block
width

1.25
rem
height

1.25
rem

@media (max-width: $ MQMobile)
  .sidebar-button
  display block
</style>
