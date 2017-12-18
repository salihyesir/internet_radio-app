using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(RadioFirebase.Startup))]
namespace RadioFirebase
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
