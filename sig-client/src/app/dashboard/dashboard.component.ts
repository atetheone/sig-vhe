import { Component, OnInit } from '@angular/core';
import { DashboardService } from './dashboard.service';
import * as Chartist from 'chartist';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  stats = [
    { category: 'Total Cases', value: 0, icon: 'content_copy', info: 'Number of total cases' },
    { category: 'New Cases', value: 0, icon: 'info_outline', info: 'Number of new cases' },
    { category: 'Recovered', value: 0, icon: 'update', info: 'Number of recovered cases' },
    { category: 'Deaths', value: 0, icon: 'warning', info: 'Number of deaths' }
  ];

  dailyCasesIncrease = 0;
  lastUpdated = 0;

  taskTabs = [
    { label: 'Bugs', icon: 'bug_report', tasks: [], active: true },
    { label: 'Website', icon: 'code', tasks: [], active: false },
    { label: 'Server', icon: 'cloud', tasks: [], active: false }
  ];

  cases = [];

  constructor(private dashboardService: DashboardService) { }

  ngOnInit() {
    this.loadStats();
    this.loadChartData();
    this.loadTasks();
    this.loadCaseData();
  }

  loadStats() {
    this.dashboardService.getStats().subscribe((data: any) => {
      this.stats[0].value = data.totalCases;
      this.stats[1].value = data.newCases;
      this.stats[2].value = data.recovered;
      this.stats[3].value = data.deaths;
      this.dailyCasesIncrease = data.dailyIncrease;
      this.lastUpdated = data.lastUpdatedMinutes;
    });
  }

  loadChartData() {
    this.dashboardService.getChartData().subscribe((data: any) => {
      const dailySalesChart = new Chartist.Line('#dailySalesChart', data.dailySales, this.getChartOptions());
      this.startAnimationForLineChart(dailySalesChart);

      const websiteViewsChart = new Chartist.Bar('#websiteViewsChart', data.websiteViews, this.getChartOptions(), this.getResponsiveOptions());
      this.startAnimationForBarChart(websiteViewsChart);

      const completedTasksChart = new Chartist.Line('#completedTasksChart', data.completedTasks, this.getChartOptions());
      this.startAnimationForLineChart(completedTasksChart);
    });
  }

  loadTasks() {
    this.dashboardService.getTasks().subscribe((data: any) => {
      this.taskTabs.forEach(tab => {
        tab.tasks = data[tab.label.toLowerCase()] || [];
      });
    });
  }

  loadCaseData() {
    this.dashboardService.getCases().subscribe((data: any) => {
      this.cases = data;
    });
  }

  startAnimationForLineChart(chart) {
    let seq: any, delays: any, durations: any;
    seq = 0;
    delays = 80;
    durations = 500;
    chart.on('draw', function(data) {
      if (data.type === 'line' || data.type === 'area') {
        data.element.animate({
          d: {
            begin: 600,
            dur: 700,
            from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
            to: data.path.clone().stringify(),
            easing: Chartist.Svg.Easing.easeOutQuint
          }
        });
      } else if (data.type === 'point') {
        seq++;
        data.element.animate({
          opacity: {
            begin: seq * delays,
            dur: durations,
            from: 0,
            to: 1,
            easing: 'ease'
          }
        });
      }
    });
    seq = 0;
  }

  startAnimationForBarChart(chart) {
    let seq2: any, delays2: any, durations2: any;
    seq2 = 0;
    delays2 = 80;
    durations2 = 500;
    chart.on('draw', function(data) {
      if (data.type === 'bar') {
        seq2++;
        data.element.animate({
          opacity: {
            begin: seq2 * delays2,
            dur: durations2,
            from: 0,
            to: 1,
            easing: 'ease'
          }
        });
      }
    });
    seq2 = 0;
  }

  getChartOptions() {
    return {
      lineSmooth: Chartist.Interpolation.cardinal({
        tension: 0
      }),
      low: 0,
      high: 1000,
      chartPadding: { top: 0, right: 0, bottom: 0, left: 0 }
    };
  }

  getResponsiveOptions(): Chartist.IResponsiveOptionTuple<Chartist.IBarChartOptions>[] {
    return [
      ['screen and (max-width: 640px)', {
        seriesBarDistance: 5,
        axisX: {
          labelInterpolationFnc: function (value) {
            return value[0];
          }
        }
      }]
    ];
  }

  selectTab(tab) {
    this.taskTabs.forEach(t => t.active = false);
    tab.active = true;
  }

  editTask(task) {
    // Logic for editing task
  }

  removeTask(task) {
    // Logic for removing task
  }
}
