module.exports = function(grunt) {

  var config = require('./package.json');
  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    clean: {
      dist: {
        src: [config.paths.dist + '/lib/**/*']
      },
      options: {
        force: true
      }
    },
    bowercopy: {
      options: {
        srcPrefix: 'bower_components'
      },
      vendorlibs: {
        options: {
          destPrefix: config.paths.dist
        },
        files: {
          'lib/bootstrap/bootstrap.min.css': 'bootstrap/dist/css/bootstrap.min.css',
          'lib/bootstrap/bootstrap-theme.min.css': 'bootstrap/dist/css/bootstrap-theme.min.css',
          'lib/bootstrap/bootstrap.min.js': 'bootstrap/dist/js/bootstrap.min.js',
          'lib/fonts/': 'bootstrap/dist/fonts/*',
          'lib/jquery/jquery.min.js': 'jquery/dist/jquery.min.js',
          'lib/jquery/jquery.min.map': 'jquery/dist/jquery.min.map',
          'lib/react/react.js': 'react/react.js',
          'lib/react/react.min.js': 'react/react.min.js'
        }
      }
    },
    babel: {
      options: {
        sourceMap: true
      },
      react: {
        files: {
          'src/main/resources/static/js/giantbombsearch.js': 'src/main/jsx/giantbombsearch.js',
          'src/main/resources/static/js/wishlist.js': 'src/main/jsx/wishlist.js'
        }
      }
    },
    watch: {
      jsx: {
        files: ['src/main/jsx/*.js'],
        tasks: ['babel'],
        options: {
          spawn: false,
          livereload: true
        },
      },
      html: {
        files: ['src/main/resources/static/index.html', 'src/main/resources/static/wishlist.html'],
        options: {
          livereload: true
        }
      }
    },
    connect: {
      dev: {
        options: {
          base: config.paths.dist,
          livereload: true
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-bowercopy');
  grunt.loadNpmTasks('grunt-babel');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-connect');

  // Default task(s).
  grunt.registerTask('default', ['clean', 'bowercopy', 'babel']);
  grunt.registerTask('dev', ['babel', 'connect', 'watch']);

};
