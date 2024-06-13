/** @type {import('tailwindcss').Config} */
const colors = require('tailwindcss/colors')


module.exports = {
    content: ["../resources/templates/**/*.{html,js}"], // it will be explained later
    theme: {
      extend: {
        colors: {
          'floor': "#D7D7D7",
          'element': "#F3F3F3",
          'element-h': "#EBEBEB",
          'handle': "#828282",
          'handle-h': "#565656",

          'speech': "#252525",
          'speech-h': "#4E4E4E",

          // dark ones
          'floor-dark': "#252525",
          'element-dark':"#101010",
          'element-dark-hover':"#080808",
          'handle-dark': "#565656",
          'handle-dark-hover': "#414141",

          'speech-dark': "#EDEDED",
          'speech-dark-hover': "#b5b5b5",
        }
      },
    },
    plugins: [],
  }