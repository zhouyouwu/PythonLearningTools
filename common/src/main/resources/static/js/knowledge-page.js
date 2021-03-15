// /* globals Chart:false, feather:false */
//
// (function () {
//   'use strict'
//
//   feather.replace()
//
//   // Graphs
//   var ctx = document.getElementById('myChart')
//   // eslint-disable-next-line no-unused-vars
//   var myChart = new Chart(ctx, {
//     type: 'line',
//     data: {
//       labels: [
//         'Sunday',
//         'Monday',
//         'Tuesday',
//         'Wednesday',
//         'Thursday',
//         'Friday',
//         'Saturday'
//       ],
//       datasets: [{
//         data: [
//           15339,
//           21345,
//           18483,
//           24003,
//           23489,
//           24092,
//           12034
//         ],
//         lineTension: 0,
//         backgroundColor: 'transparent',
//         borderColor: '#007bff',
//         borderWidth: 4,
//         pointBackgroundColor: '#007bff'
//       }]
//     },
//     options: {
//       scales: {
//         yAxes: [{
//           ticks: {
//             beginAtZero: false
//           }
//         }]
//       },
//       legend: {
//         display: false
//       }
//     }
//   })
// })()

var onResize = function() {
  // apply dynamic padding at the top of the body according to the fixed navbar height
  $(".tab-content").css("padding-left", $(".nav-sidebar").width()+10);
};

// attach the function to the window resize event
window.onresize = onResize;

// call it also when the page is ready after load or reload
$(function() {
  onResize();
});
